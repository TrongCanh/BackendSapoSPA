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
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.google.gson.Gson;
import com.sapo.model.Item;
import com.sapo.model.ItemDetails;
import com.sapo.model.ItemList;

public class PostJSON {
	private final String USER_AGENT = "Mozilla/5.0";
	public static final String TEST_KEY = "26f76c014a453d1fb248f35e2a42d3c655fd97a9e671b79d3dfa59eb876bb43e";
	public static final int PARTNER_ID = 840386;

//	public List<Item> items() throws IOException{
//		List<Item> listItem= new ArrayList<Item>();
//		String url_str = "https://partner.uat.shopeemobile.com/api/v1/items/get";
//		URL url = new URL (url_str);
//		
//		HttpURLConnection con = (HttpURLConnection)url.openConnection();
//		con.setRequestMethod("POST");
//		
//		con.setRequestProperty("Content-Type", "application/json");
//		con.setRequestProperty("Accept", "application/json");
//		
//		con.setDoOutput(true);
//		
//		//JSON String need to be constructed for the specific resource. 
//		//We may construct complex JSON using any third-party JSON libraries such as jackson or org.json
//		String timestamp = String.valueOf(Instant.now().getEpochSecond());
//		System.out.println("__time stamp: " + timestamp);
////		String jsonInputString = String.format("{\"partner_id\": %d, \"shopid\": %d, \"timestamp\": %s}", PARTNER_ID, SHOP_ID, timestamp);
//		
//		String jsonInputString = String.format("{\"pagination_offset\": %d, \"pagination_entries_per_page\": %d,\"partner_id\": %d, \"shopid\": %d, \"timestamp\": %s}", 0, 10, PARTNER_ID, SHOP_ID, timestamp);
//		
//		System.out.println("__request body: " + jsonInputString);
//		
//		String signature_base_string = url_str+"|"+jsonInputString;
//		con.setRequestProperty("Content-Length", String.valueOf(jsonInputString.length()));
//		System.out.println("__signature_base_string: "+signature_base_string);
//		
//		String authSignature;
//		try {
//			authSignature = getAuthSignature(TEST_KEY, signature_base_string);
//			con.setRequestProperty("Authorization", authSignature );
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		try(OutputStream os = con.getOutputStream()){
//			byte[] input = jsonInputString.getBytes("utf-8");
//			os.write(input, 0, input.length);			
//		}
//
//		int code = con.getResponseCode();
//		System.out.println("__respond code: "+ code);
//		
//		try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))){
//			StringBuilder response = new StringBuilder();
//			String responseLine = null;
//			while ((responseLine = br.readLine()) != null) {
//				response.append(responseLine.trim());
//			}
//			System.out.println(response.toString());
//			Gson gson = new Gson();
//			ItemList items= gson.fromJson(response.toString(), ItemList.class);
//			listItem= items.getItems();
//		}
//		return listItem;
//		
//	}

	// 1234567892345678
	public List<Item> items(Long SHOP_ID) throws IOException {
		List<Item> listItem = new ArrayList<Item>();
		String timestamp = String.valueOf(Instant.now().getEpochSecond());
		String url_str = "https://partner.uat.shopeemobile.com/api/v1/items/get";
		String jsonInputString = String.format(
				"{\"pagination_offset\": %d, \"pagination_entries_per_page\": %d,\"partner_id\": %d, \"shopid\": %d, \"timestamp\": %s}",
				0, 10, PARTNER_ID, SHOP_ID, timestamp);
		String json = json(url_str, jsonInputString);
		Gson gson = new Gson();
		ItemList items = gson.fromJson(json, ItemList.class);
		listItem = items.getItems();
		return listItem;
	}

	public Item item(Long ITEM_ID, Long SHOP_ID) throws IOException {
		String timestamp = String.valueOf(Instant.now().getEpochSecond());
		String url_str = "https://partner.uat.shopeemobile.com/api/v1/item/get";
		String jsonInputString = String.format(
				"{\"item_id\": %d,\"partner_id\": %d, \"shopid\": %d, \"timestamp\": %s}", ITEM_ID, PARTNER_ID, SHOP_ID,
				timestamp);
		String json = json(url_str, jsonInputString);
		Gson gson = new Gson();
		ItemDetails itemDetails = gson.fromJson(json, ItemDetails.class);
		Item item = itemDetails.getItem();
		return item;
	}

	public Item price(Long ITEM_ID, Long SHOP_ID, float price) throws IOException {
		String timestamp = String.valueOf(Instant.now().getEpochSecond());
		String url_str = "https://partner.uat.shopeemobile.com/api/v1/items/update_price";
		String jsonInputString = String.format(
				"{\"item_id\": %d,\"partner_id\": %d, \"shopid\": %d, \"timestamp\": %s, \"price\" :%f}", ITEM_ID,
				PARTNER_ID, SHOP_ID, timestamp, price);
		String json = json(url_str, jsonInputString);
		Gson gson = new Gson();
		ItemDetails itemDetails = gson.fromJson(json, ItemDetails.class);
		Item item = itemDetails.getItem();
		return item;

	}

	public List<Item> rival(Long ITEM_ID, Long SHOP_ID) throws Exception {
		List<Item> listItem = new ArrayList<Item>();
		String key = item(ITEM_ID, SHOP_ID).getName();
		String url = "https://shopee.vn/api/v2/search_items?by=relevancy&keyword=" + encodeValue(key)
		+ "&limit=50&newest=0&order=desc&page_type=search";
		String json = sendGet(url);
		Gson gson = new Gson();
		ItemList items = gson.fromJson(json, ItemList.class);
		listItem = items.getItems();
		for (Item item : listItem) {
			item.setItem_id(item.getItemid());
		}
		return listItem;

	}
	public Item itemDetails(Long itemid, Long shopid) throws Exception {
		String url = "https://shopee.vn/api/v2/item/get?itemid="+itemid+"&shopid="+shopid;
		String json = sendGet(url);
		Gson gson = new Gson();
		ItemDetails itemDetails = gson.fromJson(json, ItemDetails.class);
		Item item = itemDetails.getItem();
		item.setPrice(item.getPrice()/10000);
		String[] images = item.getImages();
		int k=images.length;
		for (int i=0;i<k;i++) {
			images[i] = "https://cf.shopee.vn/file/"+images[i];
		}
		item.setImages(images);
		return item;
	}
	public String sendGet(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

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

	public String json(String url_str, String jsonInputString) throws IOException {
		String json = null;
		URL url = new URL(url_str);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");

		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setDoOutput(true);

		// JSON String need to be constructed for the specific resource.
		// We may construct complex JSON using any third-party JSON libraries such as
		// jackson or org.json
		String timestamp = String.valueOf(Instant.now().getEpochSecond());
		System.out.println("__time stamp: " + timestamp);
//	String jsonInputString = String.format("{\"partner_id\": %d, \"shopid\": %d, \"timestamp\": %s}", PARTNER_ID, SHOP_ID, timestamp);	
		System.out.println("__request body: " + jsonInputString);

		String signature_base_string = url_str + "|" + jsonInputString;
		con.setRequestProperty("Content-Length", String.valueOf(jsonInputString.length()));
		System.out.println("__signature_base_string: " + signature_base_string);

		String authSignature;
		try {
			authSignature = getAuthSignature(TEST_KEY, signature_base_string);
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

	private static String encodeValue(String value) {
		try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex.getCause());
		}
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

//	public static byte hexToByte(String hexString) {
//	    int firstDigit = toDigit(hexString.charAt(0));
//	    int secondDigit = toDigit(hexString.charAt(1));
//	    return (byte) ((firstDigit << 4) + secondDigit);
//	}

//	private static int toDigit(char hexChar) {
//	    int digit = Character.digit(hexChar, 16);
//	    if(digit == -1) {
//	        throw new IllegalArgumentException(
//	          "Invalid Hexadecimal Character: "+ hexChar);
//	    }
//	    return digit;
//	}
}
