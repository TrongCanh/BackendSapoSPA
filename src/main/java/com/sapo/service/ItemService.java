package com.sapo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapo.model.Item;
import com.sapo.repository.ItemRepository;

@Service
public class ItemService {
	@Autowired 
	ItemRepository itemRepository;
	public Item save(Item item) {
		return itemRepository.save(item);
}

}
