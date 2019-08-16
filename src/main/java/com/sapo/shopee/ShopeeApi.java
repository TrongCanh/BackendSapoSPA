package com.sapo.shopee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sapo.dto.ItemV2;
import com.sapo.dto.ItemsV2;
import com.sapo.dto.KeyItem;
import com.sapo.dto.ShopV2;
import com.sapo.dto.Shop_covers;
import com.sapo.model.Item;
import com.sapo.model.Rating;
import com.sapo.model.Shop;
import com.sapo.repository.ItemRepository;
import com.sapo.util.Util;

@Component
public class ShopeeApi {
	@Autowired
	ItemRepository itemRepository;
	Gson gson = new Gson();
	String timestamp = String.valueOf(Instant.now().getEpochSecond());
	Logger logger = LoggerFactory.getLogger(ShopeeApi.class);

	public String getItemListV1(int offset, int entries, long shopid) throws IOException {
		String bodyStr = String.format(
				"{\"pagination_offset\": %d, \"pagination_entries_per_page\": %d,\"partner_id\": %d, \"shopid\": %d, \"timestamp\": %s}",
				0, 10, Util.PARTNER_ID, shopid, timestamp);

		return callShopeeAPI(Util.GetItemList_URLV1, bodyStr);
	}

	public String getItemDetailsV1(long item_id, long shopid) throws IOException {
		String bodyStr = String.format("{\"item_id\": %d,\"partner_id\": %d, \"shopid\": %d, \"timestamp\": %s}",
				item_id, Util.PARTNER_ID, shopid, timestamp);
		return callShopeeAPI(Util.GetItemDetail_URLV1, bodyStr);
	}

	public Item getItemDetailsV2(long itemid, long shopid) throws Exception {
		String uri = "https://shopee.vn/api/v2/item/get?itemid=" + itemid + "&shopid=" + shopid;
		Item item = gson.fromJson(callApi(uri), ItemV2.class).getItem();
		Rating item_rating = item.getItem_rating();
		if (item_rating != null) {
			item.setRating_count(item_rating.getRating_count());
			item.setRating_star(item_rating.getRating_star());
		}
		// itemRepository.save(item);
		
		return item;
	}

	public List<KeyItem> getRivals( long itemid, Long shopid) throws Exception, Exception {
		String name = getItemDetailsV2(itemid, shopid).getName();
		String url = "https://shopee.vn/api/v2/search_items/?by=relevancy&keyword=" + encodeValue(name)
				+ "&newest=0&order=desc&page_type=search";
		List<KeyItem> items = gson.fromJson(callApi(url), ItemsV2.class).getItems();
		return items;
	}

	public Shop shopInfor(long shopid) throws Exception {
		String url = "https://shopee.vn/api/v2/shop/get?shopid=" + shopid;
		Shop shop = gson.fromJson(callApi(url), ShopV2.class).getData();
		Rating rating = shop.getBuyer_rating();
		if (rating != null) {
			shop.setRating_count(rating.getRating_count());
			shop.setRating_star(rating.getRating_star());
		}
		List<Shop_covers> cover = shop.getShop_covers();
		if (cover != null) {
			String coverImg[] = new String[cover.size()];
			for (int i = 0; i < cover.size(); i++) {
				coverImg[i] = "https://cf.shopee.vn/file/" + cover.get(i).getImage_url();
			}
			shop.setImages(coverImg);
		}
		return shop;
	}

	public String updatePrice(Long ITEM_ID, Long SHOP_ID, float price) throws IOException {
		String jsonInputString = String.format(
				"{\"item_id\": %d,\"partner_id\": %d, \"shopid\": %d, \"timestamp\": %s, \"price\" :%f}", ITEM_ID,
				Util.PARTNER_ID, SHOP_ID, timestamp, price);
		return callShopeeAPI(Util.UpdatePrice, jsonInputString);

	}

	public String callApi(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", Util.USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}
//	public String callAPI(String uri) throws Exception {
//		RestTemplate restTemplate = new RestTemplate();
//		String result = restTemplate.getForObject(uri, String.class);
//		System.out.println(uri);
//		System.out.println(result);
//		return result;
//	}

	public String callShopeeAPI(String url_str, String jsonInputString) throws IOException {
		String json = null;
		URL url = new URL(url_str);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");

		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setDoOutput(true);

		String timestamp = String.valueOf(Instant.now().getEpochSecond());
		System.out.println("__time stamp: " + timestamp);
		System.out.println("__request body: " + jsonInputString);

		String signature_base_string = url_str + "|" + jsonInputString;
		con.setRequestProperty("Content-Length", String.valueOf(jsonInputString.length()));
		System.out.println("__signature_base_string: " + signature_base_string);

		String authSignature;
		try {
			authSignature = getAuthSignature(Util.KEY, signature_base_string);
			con.setRequestProperty("Authorization", authSignature);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (OutputStream os = con.getOutputStream()) {
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);
		}

		int code = con.getResponseCode();
		System.out.println("__respond code: " + code);

		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());
			json = response.toString();
		}
		return json;

	}

	public static String getAuthSignature(String secret, String message) throws Exception {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
		sha256_HMAC.init(secret_key);

		byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
		String hash = encodeHexString(bytes);

		System.out.println("__auth signature: " + hash);
		return hash;
	}

	public static String encodeHexString(byte[] byteArray) {
		StringBuffer hexStringBuffer = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			hexStringBuffer.append(byteToHex(byteArray[i]));
		}
		return hexStringBuffer.toString();
	}

	public static String byteToHex(byte num) {
		char[] hexDigits = new char[2];
		hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
		hexDigits[1] = Character.forDigit((num & 0xF), 16);
		return new String(hexDigits);
	}

	private static String encodeValue(String value) {
		try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex.getCause());
		}
	}
}