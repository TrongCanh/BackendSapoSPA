package com.sapo.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapo.model.Shop;
@Repository
@Transactional
public interface ShopRepository extends JpaRepository<Shop, Long>{
	Shop findByName(String name);
}
