package com.project.repository;



import com.project.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon ,Integer> {
    @Query(value = "SELECT * FROM shoedatabase.coupon c where c.code = ?1;", nativeQuery = true)
    public Optional<Coupon> findByCode(String code);
}
