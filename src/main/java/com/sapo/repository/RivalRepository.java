package com.sapo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sapo.model.Rival;

public interface RivalRepository extends JpaRepository<Rival, Long> {
	Rival findByItemidAndRival(Long item , long rival);
}
