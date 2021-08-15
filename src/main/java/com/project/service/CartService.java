package com.project.service;

import java.util.List;

import com.project.entity.CartDetail;

public interface CartService {
	//tinh tong so tien
	double totalAmount(List<CartDetail> listCart);

}
