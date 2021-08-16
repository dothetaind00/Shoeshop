package com.project.service;

import java.util.List;

import com.project.entity.CartDetail;

public interface CartService {
	float totalAmount(List<CartDetail> listCart);
}
