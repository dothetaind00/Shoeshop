package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.CartDetail;

import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer>{
    List<CartDetail> findAllByCartId(Integer id);
}
