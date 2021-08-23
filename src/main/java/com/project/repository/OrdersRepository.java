package com.project.repository;

import com.project.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    Page<Orders> findAllByStatusId(Integer status, Pageable pageable);

    @Query("select distinct o from Orders o where o.status.id = :status and o.timeOrder between :startdate and :enddate")
    Page<Orders> findOrdersByTime(@Param("status") Integer status,@Param("startdate") Timestamp startdate,@Param("enddate") Timestamp enddate, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Orders o SET o.status.id = :statusId WHERE o.id = :ordersId")
    void cancelOrder(@Param("ordersId") Integer ordersId, @Param("statusId") Integer statusId);

    @Query(value = "SELECT MONTH(time_order), SUM(total_amount), sum(total_cost) FROM orders Where status_id = 5 Group by MONTH(time_order) order by MONTH(time_order) asc", nativeQuery = true)
    public List<Object[]> listByMonth();

    @Query(value = "SELECT MONTH(time_order), SUM(total_amount), sum(total_cost) FROM orders Where status_id = 5 and time_order between ?1 and ?2 Group by MONTH(time_order) order by MONTH(time_order) asc;", nativeQuery = true)
    public List<Object[]> findByMonth(String firstDate, String endDate);

    @Query(value = "SELECT count(id) FROM orders where status_id = 5", nativeQuery = true)
    Integer countOrder();
}
