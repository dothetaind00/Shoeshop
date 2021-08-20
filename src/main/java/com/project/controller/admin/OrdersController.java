package com.project.controller.admin;

import com.project.entity.Orders;
import com.project.entity.OrdersDetail;
import com.project.entity.Status;
import com.project.service.OrdersService;
import com.project.service.SizeService;
import com.project.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller(value = "ordersOfAdmin")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private SizeService sizeService;

    @GetMapping("/admin/orders")
    private String getPage(){
        return "admin/orders";
    }

    @GetMapping("/api/order/{orderId}")
    @ResponseBody
    public ResponseEntity<Orders> getOrders(@PathVariable(name = "orderId") Integer orderId){
        Optional<Orders> orders = ordersService.findById(orderId);
        return orders.map(orders1 -> new ResponseEntity<>(orders1, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/api/orders/{pageNo}/{status}")
    @ResponseBody
    public Page<Orders> getAllOrders(@PathVariable(name = "pageNo") Optional<Integer> pageNo, @PathVariable(name = "status") Optional<Integer> statusId){
        int page = pageNo.orElse(1);
        int status = statusId.orElse(1);
        Pageable pageable = PageRequest.of(page - 1,8, Sort.by("timeOrder").descending());
        Page<Orders> listOrders = ordersService.findAllByStatus(status,pageable);
        return listOrders;
    }

    @GetMapping("/api/orders/{pageNo}")
    @ResponseBody
    public Page<Orders> getAllDate(@PathVariable(name = "pageNo") Optional<Integer> pageNo, @RequestParam(name = "statusid") Integer statusId,
                                   @RequestParam(name = "startdate") String startdate, @RequestParam(name = "enddate") String enddate){
        int page = pageNo.orElse(1);
        Pageable pageable = PageRequest.of(page - 1,8, Sort.by("timeOrder").descending());

        startdate = startdate + " 00:00:00";
        enddate = enddate + " 23:59:59";
        Timestamp startD = Timestamp.valueOf(startdate);
        Timestamp endD = Timestamp.valueOf(enddate);

        Page<Orders> listOrders = ordersService.findOrdersByTime(statusId, startD, endD, pageable);
        return listOrders;
    }

    @PutMapping("/api/orders/{ordersId}/{statusId}")
    @ResponseBody
    public ResponseEntity<Orders> updateOrder(@PathVariable(name = "ordersId") Integer ordersId,
                                              @PathVariable(name = "statusId") Integer statusId){

        Optional<Orders> orders = ordersService.findById(ordersId);
        if (!orders.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        if (statusId == 5){
            List<OrdersDetail> list = orders.get().getOrdersDetails();
            for (OrdersDetail detail : list){
                sizeService.updateAmount(detail.getSize().getAmount() + detail.getAmount(),detail.getSize().getId());
            }
        }



        Optional<Status> status = statusService.findById(statusId);
        orders.get().setStatus(status.get());

        ordersService.save(orders.get());

        return new ResponseEntity<>(orders.get(), HttpStatus.OK);
    }
}
