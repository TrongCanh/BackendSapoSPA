package com.sapo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sapo.model.Item;
import com.sapo.model.Shop;
import com.sapo.repository.ItemRepository;
import com.sapo.repository.ShopRepository;
import com.sapo.shopee.PostJSON;

@Component
public class ScheduledJob {
	PostJSON json = new PostJSON();
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	ItemRepository itemRepository;
	@Scheduled(fixedRate = 600000)
	public void scheduleFixedRate() throws Exception {
		List<Shop> shops = shopRepository.findAll();
		for (Shop shop : shops) {
			List<Item> listItem = json.items(shop.getId());
			List<Item> list = new ArrayList<Item>();
			for (Item item : listItem) {
				Item itemDetails = json.item(item.getItem_id(), shop.getId());
				itemRepository.save(itemDetails);
				list.add(itemDetails);
			}

		}
		System.out.println("Task-" + new Date());
	}
}
