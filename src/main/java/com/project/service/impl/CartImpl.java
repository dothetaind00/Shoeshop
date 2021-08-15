package com.project.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.entity.CartDetail;
import com.project.entity.Invoice;
import com.project.service.CartService;

@Service
public class CartImpl implements CartService{

	@Override
	public double totalAmount(List<CartDetail> listCart) {
		// TODO Auto-generated method stub
		float totalAmount = 0;
		for (CartDetail cartDetail : listCart) {
			totalAmount += cartDetail.getAmount()*cartDetail.getPrice();
		}
		return totalAmount;
	}

}
