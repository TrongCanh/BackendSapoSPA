package com.sapo.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.sapo.config.JwtTokenUtil;
import com.sapo.model.Shop;
import com.sapo.model.User;
import com.sapo.repository.ShopRepository;
import com.sapo.repository.UserRepository;
@CrossOrigin()
@RestController
public class ShopController {
	@Value("${shopee.partnerKey}")
	private String partnerKey;
	@Value("${shopee.redirectURL}")
	private String redirectURL;
	@Value("${shopee.partnerId}")
	private String partnerId;

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
		String token=calToken(redirectURL, partnerKey);
		String urlShopee="https://partner.shopeemobile.com/api/v1/shop/auth_partner?id="+partnerId+"&token="+token+"&redirect="+redirectURL;
		return urlShopee;
	}
	public static String calToken(String redirectURL, String partnerKey) {
	    String baseStr = partnerKey + redirectURL;
	    return org.apache.commons.codec.digest.DigestUtils.sha256Hex(baseStr);
	}
}
