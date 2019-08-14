package com.sapo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapo.model.Item;
import com.sapo.model.Rival;
@Repository
public interface RivalRepository extends JpaRepository<Rival, Long> {
	Rival findByItem(Item item);
}
