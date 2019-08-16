package com.sapo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sapo.model.Test;

public interface TestRepository extends JpaRepository<Test, Long> {

}
