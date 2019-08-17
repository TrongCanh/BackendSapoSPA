package com.sapo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapo.model.PriceItem;
@Repository
public interface PriceItemRepository extends JpaRepository<PriceItem, Long> {

}
