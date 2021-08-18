package com.project.service.impl;

import com.project.entity.Orders;
import com.project.repository.OrdersRepository;
import com.project.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public Orders save(Orders orders) {
        return ordersRepository.save(orders);
    }
}
