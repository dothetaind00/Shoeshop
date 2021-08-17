package com.project.service;

import com.project.entity.CartDetail;

import java.util.List;
import java.util.Optional;

public interface CartDetailService {

    CartDetail save(CartDetail cartDetail);

    List<CartDetail> saveAll(List<CartDetail> entities);

    List<CartDetail> findAllByCartId(Integer id);

    void deleteById(Integer id);

}
