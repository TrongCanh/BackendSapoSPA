package com.sapo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.sapo.config.JwtTokenUtil;
import com.sapo.dto.KeyItem;
import com.sapo.model.Shop;
import com.sapo.model.User;
import com.sapo.repository.ShopRepository;
import com.sapo.repository.UserRepository;
import com.sapo.shopee.ShopeeApi;
import com.sapo.util.Util;
@CrossOrigin()
@RestController
public class ShopController {
	@Autowired
	ShopeeApi shopeeApi;
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/shop")
	public Shop addshop(@RequestBody Shop newShop, @RequestHeader("Authorization") String token) {
		if (token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		String username = jwtTokenUtil.getUsernameFromToken(token);
		User  user = userRepository.findByUsername(username);
		newShop.setUser(user);
		shopRepository.save(newShop);
//		userRepository.save(user);
		return newShop;
	}
	@GetMapping("/shopInfor/{shopid}")
	public Shop shopInfor(@PathVariable("shopid") Long shopid) throws Exception {
		return shopeeApi.shopInfor(shopid);
	}
	@GetMapping("/shopRival/{shop_id}/{item_id}")
	public List<Shop> shopRival(@PathVariable("item_id") Long item_id,@PathVariable("shop_id") Long shop_id) throws Exception{
		List<KeyItem> keys= shopeeApi.getRivals(item_id,shop_id);
		List<Shop> shops = new ArrayList<Shop>();
		for (KeyItem key : keys) {
			Shop shop = shopeeApi.shopInfor(key.getShopid());
			shop.setItemid(key.getItemid());
			shops.add(shop);
		}
		return shops;
	}
	@GetMapping("/shop")
	public Set<Shop> shops(@RequestHeader("Authorization") String token) {
		if (token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		String username = jwtTokenUtil.getUsernameFromToken(token);
		User  user = userRepository.findByUsername(username);
		Set<Shop> shops = user.getShops();
//		userRepository.save(user);
		return shops;
	}
	@GetMapping("/shopee")
	public String shopee() {
		String token=calToken(Util.redirectURL, Util.KEY);
		String urlShopee="https://partner.shopeemobile.com/api/v1/shop/auth_partner?id="+Util.PARTNER_ID+"&token="+token+"&redirect="+Util.redirectURL;
		return urlShopee;
	}
	public static String calToken(String redirectURL, String partnerKey) {
	    String baseStr = partnerKey + redirectURL;
	    return org.apache.commons.codec.digest.DigestUtils.sha256Hex(baseStr);
	}
}
