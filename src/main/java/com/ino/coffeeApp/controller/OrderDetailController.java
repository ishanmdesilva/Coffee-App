package com.ino.coffeeApp.controller;

import com.ino.coffeeApp.dto.OrderDetailCreateRequest;
import com.ino.coffeeApp.dto.OrderDetailUpdateRequest;
import com.ino.coffeeApp.entity.OrderDetail;
import com.ino.coffeeApp.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/order")
@RestController
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderDetailById(@PathVariable Long id) {
        OrderDetail orderDetail = orderDetailService.getOrderById(id);

        return new ResponseEntity<>(orderDetail, HttpStatus.OK);
    }

    @GetMapping("/shop-detail-id/{shopId}")
    public ResponseEntity<Object> getOrderDetailByShopId(@PathVariable Long shopId) {
        List<OrderDetail> orderDetailList = orderDetailService.getOrderByShopId(shopId);

        return new ResponseEntity<>(orderDetailList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> saveOrder(@RequestBody OrderDetailCreateRequest request) {
        OrderDetail orderDetail = orderDetailService.saveOrderDetail(request);

        return new ResponseEntity<>(orderDetail, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long id, @RequestBody OrderDetailUpdateRequest request) {
        OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, request);

        return new ResponseEntity<>(orderDetail, HttpStatus.OK);
    }

    @PutMapping("/order-cancelled/{id}")
    public ResponseEntity<Object> orderCancellation(@PathVariable Long id) {
        OrderDetail orderDetail = orderDetailService.orderCancellation(id);

        return new ResponseEntity<>(orderDetail, HttpStatus.OK);
    }

    @PutMapping("/order-completed/{id}")
    public ResponseEntity<Object> orderCompleted(@PathVariable Long id) {
        OrderDetail orderDetail = orderDetailService.orderCompleted(id);

        return new ResponseEntity<>(orderDetail, HttpStatus.OK);
    }

}
