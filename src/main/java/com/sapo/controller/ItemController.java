package com.sapo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sapo.dto.KeyItem;
import com.sapo.model.Category;
import com.sapo.model.Item;
import com.sapo.repository.CategoryRepository;
import com.sapo.repository.ItemRepository;
import com.sapo.shopee.ShopeeApi;
@CrossOrigin()

@RestController
public class ItemController {
	@Autowired
	ShopeeApi shopeeApi;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	CategoryRepository categoryRepository;
	
	@PutMapping("/getItems/{shop_id}")
	public List<Item> putItems(@PathVariable("shop_id") Long shopid) throws Exception {
		List<Item> items= shopeeApi.getItemListV1(0, 50, shopid);
		for (Item item : items) {
			Set<Category> categories = item.getCategories();
			Set<Category> all = categoryRepository.findByItem(item);
			if (all != null) {

				for (Category category : all) {
					categoryRepository.delete(category);
				}
				for (Category category : categories) {
					category.setItem(item);
				//	categoryRepository.save(category);
				}
			}
			itemRepository.save(item);
		}
		return items;
	}
	@GetMapping("/getItems/{shop_id}")
	public List<Item> getItems(@PathVariable("shop_id") Long shopid){
		List<Item> items = itemRepository.findByShopid(shopid);
		List<Item> list = new ArrayList<Item>();
		for (Item item : items) {
			if(item.getShopid()==shopid)
				list.add(item);			
		}
		return items;
	}
	@GetMapping("/getItem.v1/{shop_id}/{item_id}")
	public String getItem1(@PathVariable("shop_id") Long shopid, @PathVariable("item_id") Long itemid)
			throws IOException {
		return shopeeApi.getItemDetailsV1(itemid, shopid);
	}

	@GetMapping("/getItem.v2/{shop_id}/{item_id}")
	public Item getItem2(@PathVariable("shop_id") Long shopid, @PathVariable("item_id") Long itemid) throws Exception {
		Item item = shopeeApi.getItemDetailsV2(itemid, shopid);
//		itemRepository.save(item);
		Set<Category> categories = item.getCategories();
		Set<Category> all = categoryRepository.findByItem(item);
		if (all != null) {

			for (Category category : all) {
				categoryRepository.delete(category);
			}
			for (Category category : categories) {
				category.setItem(item);
			//	categoryRepository.save(category);
			}
		}
		itemRepository.save(item);
		return item;
	}

	@GetMapping("/getRivals/{shop_id}/{item_id}")
	public List<Item> getRivals(@PathVariable("item_id") Long item_id, @PathVariable("shop_id") Long shop_id)
			throws Exception {
		List<KeyItem> keys = shopeeApi.getRivals(item_id, shop_id);
		List<Item> items = new ArrayList<Item>();
		for (KeyItem key : keys) {
			Item item = shopeeApi.getItemDetailsV2(key.getItemid(), key.getShopid());
			items.add(item);
		}
		return items;
	}
	@GetMapping("/item/{shop_id}/{item_id}")
	public Item getItem(@PathVariable("item_id") Long item_id, @PathVariable("shop_id") Long shop_id)
			throws Exception {
		Item item = shopeeApi.getItemDetailsV2(item_id, shop_id);
		return item;
	}
	@PostMapping("/item")
	public Item postItem(@RequestBody Item item) throws Exception {
		itemRepository.save(item);
		return item;
	}

	@PutMapping("/updatePrice/{shop_id}/{item_id}/{price}")
	public String updatePrice(@PathVariable("shop_id") Long shopid, @PathVariable("item_id") Long itemid,
			@PathVariable("price") float price) throws IOException {
		return shopeeApi.updatePrice(itemid, shopid, price);
	}
}
