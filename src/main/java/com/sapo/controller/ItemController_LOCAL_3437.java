package com.sapo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sapo.model.Item;
import com.sapo.repository.ItemRepository;
import com.sapo.shopee.PostJSON;
@CrossOrigin()
@RestController
public class ItemController {
	PostJSON json = new PostJSON();
	@Autowired
	ItemRepository itemRepository;
	@PutMapping("/items/{id}")
	public List<Item> itemsUpdate(@PathVariable("id") Long SHOP_ID) throws IOException{
		List<Item> listItem = json.items(SHOP_ID);
		List<Item> list = new ArrayList<Item>();
		for (Item item : listItem) {
			Item itemDetails =json.item(item.getItem_id(), SHOP_ID);
			itemRepository.save(itemDetails);
			list.add(itemDetails);
		}
		return list;
	}
	@GetMapping("/items/{id}")
	public List<Item> items(@PathVariable("id") Long SHOP_ID) throws IOException{
		List<Item> list = itemRepository.findAll();
		return list;
	}
	@GetMapping("/item/{shop_id}/{item_id}")
	public Item item(@PathVariable("shop_id") Long SHOP_ID,@PathVariable("item_id") Long ITEM_ID) throws IOException{
		return json.item(ITEM_ID, SHOP_ID);
	}
	@PutMapping("/item/price/{shop_id}/{item_id}/{price}")
	public Item itemPrice(@PathVariable("shop_id") Long SHOP_ID,@PathVariable("item_id") Long ITEM_ID,@PathVariable("price") float price) throws IOException {
		json.price(ITEM_ID, SHOP_ID, price);
		Item item = json.item(ITEM_ID, SHOP_ID);
		itemRepository.save(item);
		return item;
	}
	@GetMapping("/rival/{shop_id}/{item_id}")
	public List<Item> itemRival(@PathVariable("shop_id") Long SHOP_ID,@PathVariable("item_id") Long ITEM_ID) throws Exception{
		List<Item> list = json.rival(ITEM_ID, SHOP_ID);
		List<Item> items= new ArrayList<Item>();
		for (Item item : list) {
			Item itemDetails = json.itemDetails(item.getItem_id(), item.getShopid());
			itemDetails.setItem_id(itemDetails.getItemid());
			items.add(itemDetails);
		}
		return items;
	}
	@GetMapping("/itemDetails/{shop_id}/{item_id}")
	public Item itemDetails(@PathVariable("shop_id") Long SHOP_ID,@PathVariable("item_id") Long ITEM_ID) throws Exception{
		return json.itemDetails(ITEM_ID, SHOP_ID);
	}
//	@GetMapping("/google/{shop_id}/{item_id}")
//	public String google(@PathVariable("shop_id") Long SHOP_ID,@PathVariable("item_id") Long ITEM_ID) throws Exception {
//		return json.rival(ITEM_ID, SHOP_ID);
//	}
	
}
