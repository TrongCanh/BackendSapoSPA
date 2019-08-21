package com.sapo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapo.model.AutoPrice;
@Repository
public interface AutoPriceRepository extends JpaRepository<AutoPrice, Long> {

}
