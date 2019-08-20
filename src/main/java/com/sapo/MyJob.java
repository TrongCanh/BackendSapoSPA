package com.sapo;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sapo.model.Category;
import com.sapo.model.Item;
import com.sapo.model.ItemPrice;
import com.sapo.model.Rival;
import com.sapo.repository.CategoryRepository;
import com.sapo.repository.ItemPriceRepository;
import com.sapo.repository.ItemRepository;
import com.sapo.repository.RivalRepository;
import com.sapo.shopee.ShopeeApi;

@Component
public class MyJob {
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ShopeeApi shopeeApi;
	@Autowired
	RivalRepository rivalRepository;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	ItemPriceRepository itemPriceRepository;

	@Scheduled(cron = "0 */1 * * * ?")
	public void scheduleFixedDelayTask() throws Exception {
		List<Rival> rivals = rivalRepository.findAll();
		for (Rival rival : rivals) {
			Item item = shopeeApi.getItemDetailsV2(rival.getRival(), rival.getOpponent());
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
			List<ItemPrice> priceList = itemPriceRepository.findByItem(item);
			if (priceList.size() > 0) {
				if (priceList.get(priceList.size() - 1).getPrice() != item.getItemPrice().getPrice()
						&& rival.isAuto()) {

				}
			}
			priceList.add(item.getItemPrice());
			item.setItemPrices(priceList);
			itemRepository.save(item);
		}
		System.out.println("Task1 - " + new Date());
	}

}
