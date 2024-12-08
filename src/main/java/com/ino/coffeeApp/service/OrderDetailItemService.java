package com.ino.coffeeApp.service;

import com.ino.coffeeApp.dto.MenuItemAddRequest;
import com.ino.coffeeApp.entity.OrderDetail;
import com.ino.coffeeApp.entity.OrderDetailItem;

import java.util.List;

public interface OrderDetailItemService {

    List<OrderDetailItem> getOrderDetailItemByOrderDetailId(Long orderDetailId);

    OrderDetailItem saveOrderDetailItem(OrderDetail orderDetail, MenuItemAddRequest request);

}
