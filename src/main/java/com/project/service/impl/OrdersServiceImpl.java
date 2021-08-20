package com.project.service.impl;

import com.project.entity.Orders;
import com.project.repository.OrdersRepository;
import com.project.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public Orders save(Orders orders) {
        return ordersRepository.save(orders);
    }

    @Override
    @Transactional
    public Optional<Orders> findById(Integer id) {
        Optional<Orders> orders = ordersRepository.findById(id);
        orders.get().getOrdersDetails().forEach(ordersDetail -> {
            ordersDetail.getProduct();
            ordersDetail.getSize();
        });
        return orders;
    }

    @Override
    public Page<Orders> findAllByStatus(Integer status, Pageable pageable) {
        return ordersRepository.findAllByStatusId(status, pageable);
    }

    @Override
    public Page<Orders> findOrdersByTime(Integer status,Timestamp startdate, Timestamp enddate, Pageable pageable) {
        return ordersRepository.findOrdersByTime(status,startdate,enddate,pageable);
    }
}
