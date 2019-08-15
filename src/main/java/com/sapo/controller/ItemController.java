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
import com.sapo.model.ItemList;
import com.sapo.model.ItemPrice;
import com.sapo.model.Rival;
import com.sapo.repository.ItemPriceRepository;
import com.sapo.repository.ItemRepository;
import com.sapo.repository.RivalRepository;
import com.sapo.shopee.PostJSON;

@CrossOrigin()
@RestController
public class ItemController {
	PostJSON json = new PostJSON();
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	RivalRepository rivalRepository;
	@Autowired
	ItemPriceRepository itemPriceRepository;
	@PutMapping("/items/{item_id}")
	public List<Item> itemsUpdate(@PathVariable("item_id") Long SHOP_ID) throws Exception {
		List<Item> listItem = json.items(SHOP_ID);
		List<Item> list = new ArrayList<Item>();
		for (Item item : listItem) {
			Item itemDetails = json.item(item.getItem_id(), SHOP_ID);
			itemRepository.save(itemDetails);
			list.add(itemDetails);
		}
		return list;
	}

//	@PutMapping("/rivals/{shop_id}/{item_id}")
//	public List<Item> itemRival(@PathVariable("shop_id") Long SHOP_ID, @PathVariable("item_id") Long ITEM_ID)
//			throws Exception {
//		List<Item> list = json.rival(ITEM_ID, SHOP_ID);
//		List<Item> items = new ArrayList<Item>();
//		for (Item item : list) {
//			Item itemDetails = json.itemDetails(item.getItem_id(), item.getShopid());
//			itemDetails.setItem_id(itemDetails.getItemid());
//			Item myItem = json.item(ITEM_ID, SHOP_ID);
//			Rival rival = new Rival(); 
//			ItemPrice itemPrice = new ItemPrice();
//			rival.setItem(myItem);
//			rival.setRival(itemDetails.getItem_id());
//			rivalRepository.save(rival);
//			itemRepository.save(itemDetails);
//			items.add(itemDetails);
//		}
//		return items;
//	}
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
	@PutMapping("/i/{shop_id}/{item_id}")
	public List<Item> itemDetail(@PathVariable("shop_id") Long SHOP_ID,@PathVariable("item_id") Long ITEM_ID) throws Exception{
		List<Item> list = itemRepository.findAll();
		List<Item> items= new ArrayList<Item>();
		for (Item item : list) {
			Item itemDetails = json.itemDetails(ITEM_ID, SHOP_ID);
			itemDetails.setItem_id(itemDetails.getItemid());
			itemRepository.save(itemDetails);
			items.add(itemDetails);
			break;
		}
		return items;
	}
	@GetMapping("/items/{id}")
	public List<Item> items(@PathVariable("id") Long SHOP_ID) throws IOException {
		List<Item> list = itemRepository.findAll();
		List<Item> itemsList = new ArrayList<Item>();
		for (Item item : list) {
			if(item.getShopid()==SHOP_ID)
				itemsList.add(item);
		}
		return list;
	}

	@GetMapping("/item/{shop_id}/{item_id}")
	public Item item(@PathVariable("shop_id") Long SHOP_ID, @PathVariable("item_id") Long ITEM_ID) throws Exception {
		return json.item(ITEM_ID, SHOP_ID);
	}

	@PutMapping("/item/price/{shop_id}/{item_id}/{price}")
	public Item itemPrice(@PathVariable("shop_id") Long SHOP_ID, @PathVariable("item_id") Long ITEM_ID,
			@PathVariable("price") float price) throws Exception {
		json.price(ITEM_ID, SHOP_ID, price);
		Item item = json.item(ITEM_ID, SHOP_ID);
		itemRepository.save(item);
		return item;
	}

	@GetMapping("/itemDetails/{shop_id}/{item_id}")
	public Item itemDetails(@PathVariable("shop_id") Long SHOP_ID, @PathVariable("item_id") Long ITEM_ID)
			throws Exception {
		return json.itemDetails(ITEM_ID, SHOP_ID);
	}
//	@GetMapping("/google/{shop_id}/{item_id}")
//	public String google(@PathVariable("shop_id") Long SHOP_ID,@PathVariable("item_id") Long ITEM_ID) throws Exception {
//		return json.rival(ITEM_ID, SHOP_ID);
//	}

}
