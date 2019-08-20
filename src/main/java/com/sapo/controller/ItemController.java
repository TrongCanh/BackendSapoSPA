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
import com.sapo.model.ItemPrice;
import com.sapo.model.Rival;
import com.sapo.repository.CategoryRepository;
import com.sapo.repository.ItemPriceRepository;
import com.sapo.repository.ItemRepository;
import com.sapo.repository.RivalRepository;
import com.sapo.shopee.ShopeeApi;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ItemController {
	@Autowired
	ShopeeApi shopeeApi;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ItemPriceRepository itemPriceRepository;
	@Autowired
	RivalRepository rivalRepository;

	@PutMapping("/getItems/{shop_id}")
	public List<Item> putItems(@PathVariable("shop_id") Long shopid) throws Exception {
		List<Item> items = shopeeApi.getItemListV2(shopid);
		for (Item item : items) {
			Set<Category> categories = item.getCategories();
			Set<Category> all = categoryRepository.findByItem(item);
			if (all != null) {

				for (Category category : all) {
					categoryRepository.delete(category);
				}
				for (Category category : categories) {
					category.setItem(item);
				}
			}
//			Set<ItemPrice> priceList = itemPriceRepository.findByItem(item);
//			priceList.add(item.getItemPrice());
//			item.setItemPrices(priceList);
			itemRepository.save(item);
		}
		return items;
	}

	@GetMapping("/getItems/{shop_id}")
	public List<Item> getItems(@PathVariable("shop_id") Long shopid) {
		List<Item> items = itemRepository.findByShopid(shopid);
		return items;
	}

	@GetMapping("/item/{shopid}/{itemid}")
	public Item getItem(@PathVariable("shopid") Long shopid, @PathVariable("itemid") Long itemid) throws Exception {
		Item item = shopeeApi.getItemDetailsV2(itemid, shopid);
		Set<Category> categories = item.getCategories();
		Set<Category> all = categoryRepository.findByItem(item);
		if (all != null) {

			for (Category category : all) {
				categoryRepository.delete(category);
			}
			for (Category category : categories) {
				category.setItem(item);
			}
		}
//		Set<ItemPrice> priceList = itemPriceRepository.findByItem(item);
//		priceList.add(item.getItemPrice());
//		item.setItemPrices(priceList);
		itemRepository.save(item);

		return item;
	}

	@GetMapping("/getItem.v1/{shopid}/{itemid}")
	public String getItem1(@PathVariable("shopid") Long shopid, @PathVariable("itemid") Long itemid)
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
				// categoryRepository.save(category);
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

	@PostMapping("/item")
	public Item postItem(@RequestBody Item item) throws Exception {
		itemRepository.save(item);
		return item;
	}

	@PostMapping("/rival")
	public Rival postRival(@RequestBody Rival rival) throws Exception {
		if (rivalRepository.findByItemidAndRivalItemid(rival.getItemid(), rival.getRivalItemid())==null) {
			Rival newRival = new Rival(rival.getItemid(), rival.getShopid(), rival.getShopid(), rival.getRivalItemid());
			rivalRepository.save(newRival);
		}
		Item item = shopeeApi.getItemDetailsV2(rival.getRivalItemid(), rival.getRivalShopid());
		Set<Category> categories = item.getCategories();
		Set<Category> all = categoryRepository.findByItem(item);
		if (all != null) {

			for (Category category : all) {
				categoryRepository.delete(category);
			}
			for (Category category : categories) {
				category.setItem(item);
			}
		}
//		Set<ItemPrice> priceList = itemPriceRepository.findByItem(item);
//		priceList.add(item.getItemPrice());
//		item.setItemPrices(priceList);
		itemRepository.save(item);

		return rival;
	}

	@PutMapping("/rival")
	public Rival putRival(@RequestBody Rival rival) {
		if (rivalRepository.findByItemidAndRivalItemid(rival.getItemid(), rival.getRivalItemid()) == null) {
			Rival newRival = rivalRepository.findByItemidAndRivalItemid(rival.getItemid(), rival.getRivalItemid());
			newRival.setAuto(rival.isAuto());
			if (rival.isAuto()==true) {
				List<Rival> rivalAuto = rivalRepository.findByItemidAndAuto(rival.getItemid(), true);
				for (Rival rival2 : rivalAuto) {
					rival2.setAuto(false);
					rivalRepository.save(rival2);
				}
			}
			newRival.setPrice(rival.getPrice());
			rivalRepository.save(rival);
		}
		return rival;
	}
	@GetMapping("/rival/{rival}")
	public List<ItemPrice> getPriceRival(@PathVariable("rival") Long itemid){
		Item item = itemRepository.findByItemid(itemid);
		return item.getItemPrices();
	}
	@PutMapping("/updatePrice/{shop_id}/{item_id}/{price}")
	public Item updatePrice(@PathVariable("shop_id") Long shopid, @PathVariable("item_id") Long itemid,
			@PathVariable("price") float price) throws Exception {
		Item item = shopeeApi.updatePrice(itemid, shopid, price);
		return item;
	}

}
