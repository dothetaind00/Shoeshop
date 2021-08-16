package com.project.service;

import java.util.List;
import java.util.Optional;

import com.project.entity.Cart;
import com.project.entity.CartDetail;

public interface CartService {
	//tinh tong so tien
	double totalCost(List<CartDetail> listCart);

	int totalAmount(List<CartDetail> listCart);

	Optional<Cart> findCartByUserId(Integer id);

	Cart save(Cart cart);

}
