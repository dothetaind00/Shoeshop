package com.project.repository;

import com.project.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    Page<Orders> findAllByStatusId(Integer status, Pageable pageable);

    @Query("select distinct o from Orders o where o.status.id = :status and o.timeOrder between :startdate and :enddate")
    Page<Orders> findOrdersByTime(@Param("status") Integer status,@Param("startdate") Timestamp startdate,@Param("enddate") Timestamp enddate, Pageable pageable);
}
