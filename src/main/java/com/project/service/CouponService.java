package com.project.service;



import com.project.entity.Coupon;

import java.util.List;
import java.util.Optional;

public interface CouponService {

    Coupon getById(Integer id);

    void deleteAll();

    void delete(Coupon entity);

    void deleteById(Integer id);

    long count();

    boolean existsById(Integer id);

    Optional<Coupon> findById(Integer id);

    List<Coupon> findAll();

    Coupon save(Coupon entity);

    public Optional<Coupon> findByCode(String code);

}