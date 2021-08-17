package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer>{

    Optional<Cart> findCartByUserId(Integer id);

    Optional<Cart> findCartByUserUserName(String username);
}
