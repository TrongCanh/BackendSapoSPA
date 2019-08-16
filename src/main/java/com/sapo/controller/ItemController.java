package com.sapo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sapo.model.Item;
import com.sapo.model.Shop;
import com.sapo.model.Test;
import com.sapo.repository.ItemRepository;
import com.sapo.repository.TestRepository;
import com.sapo.service.ItemService;
import com.sapo.shopee.ShopeeApi;

@RestController
public class ItemController {
	@Autowired
	ShopeeApi shopeeApi;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	TestRepository testRepository;
	@GetMapping("/getItems/{shop_id}")
	public String getItems(@PathVariable("shop_id") Long shopid) throws IOException {
		return shopeeApi.getItemListV1(0, 50, shopid);
	}

	@GetMapping("/getItem.v1/{shop_id}/{item_id}")
	public String getItem1(@PathVariable("shop_id") Long shopid, @PathVariable("item_id") Long itemid)
			throws IOException {
		return shopeeApi.getItemDetailsV1(itemid, shopid);
	}

	@GetMapping("/getItem.v2/{shop_id}/{item_id}")
	public Item getItem2(@PathVariable("shop_id") Long shopid, @PathVariable("item_id") Long itemid) throws Exception {
		Item item = shopeeApi.getItemDetailsV2(itemid, shopid);
		itemRepository.save(item);
		return item;
	}
	@PostMapping("/test")
	public Test postItem2(@RequestBody Test test) throws Exception {
		testRepository.save(test);
		return test;
	}
	@PostMapping("/item")
	public Item postItem(@RequestBody Item item) throws Exception {
		itemRepository.save(item);
		return item;
	}
	@GetMapping("/updatePrice/{shop_id}/{item_id}/{price}")
	public String updatePrice(@PathVariable("shop_id") Long shopid, @PathVariable("item_id") Long itemid,
			@PathVariable("price") float price) throws IOException {
		return shopeeApi.updatePrice(itemid, shopid, price);
	}
}
