package com.sapo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapo.repository.ItemRepository;
import com.sapo.shopee.ShopeeApi;

@Service
public class ItemService {
	
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	ShopeeApi shopeeApi;
}
