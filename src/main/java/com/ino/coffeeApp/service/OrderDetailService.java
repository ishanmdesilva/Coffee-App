package com.ino.coffeeApp.service;

import com.ino.coffeeApp.dto.OrderDetailCreateRequest;
import com.ino.coffeeApp.dto.OrderDetailUpdateRequest;
import com.ino.coffeeApp.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    OrderDetail getOrderById(Long id);

    List<OrderDetail> getOrderByShopId(Long shopDetailId);

    OrderDetail saveOrderDetail(OrderDetailCreateRequest request);

    OrderDetail updateOrderDetail(Long orderDetailsId, OrderDetailUpdateRequest request);

    OrderDetail orderCancellation(Long id);

    OrderDetail orderCompleted(Long id);

}
