package com.sapo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapo.model.ItemPrice;
@Repository
public interface ItemPriceRepository extends JpaRepository<ItemPrice, Long> {

}
