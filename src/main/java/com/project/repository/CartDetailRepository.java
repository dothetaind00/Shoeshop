package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.CartDetail;

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer>{

}
