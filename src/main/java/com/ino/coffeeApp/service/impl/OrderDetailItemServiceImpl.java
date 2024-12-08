package com.ino.coffeeApp.service.impl;

import com.ino.coffeeApp.dto.MenuItemAddRequest;
import com.ino.coffeeApp.entity.MenuItem;
import com.ino.coffeeApp.entity.OrderDetail;
import com.ino.coffeeApp.entity.OrderDetailItem;
import com.ino.coffeeApp.enums.StatusEnum;
import com.ino.coffeeApp.exception.ValidateRecordException;
import com.ino.coffeeApp.repository.OrderDetailItemRepository;
import com.ino.coffeeApp.service.MenuItemService;
import com.ino.coffeeApp.service.OrderDetailItemService;
import com.ino.coffeeApp.util.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class OrderDetailItemServiceImpl implements OrderDetailItemService {

    private final OrderDetailItemRepository orderDetailItemRepository;
    private final MenuItemService menuItemService;

    @Autowired
    public OrderDetailItemServiceImpl(OrderDetailItemRepository orderDetailItemRepository,
                                      MenuItemService menuItemService) {
        this.orderDetailItemRepository = orderDetailItemRepository;
        this.menuItemService = menuItemService;
    }

    @Override
    public List<OrderDetailItem> getOrderDetailItemByOrderDetailId(Long orderDetailId) {
        return orderDetailItemRepository.findByOrderDetailId(orderDetailId);
    }

    @Override
    public OrderDetailItem saveOrderDetailItem(OrderDetail orderDetail, MenuItemAddRequest request) {
        // Get menu item
        MenuItem menuItem = menuItemService.getById(Long.parseLong(request.getMenuItemId()));

        // validate menu
        if(!menuItem.getShopDetail().getId().equals(orderDetail.getShopDetailIdValue())) {
            throw new ValidateRecordException("Menu item "+ menuItem.getId() +" not in shop id "+orderDetail.getShopDetailIdValue(), ErrorCodes.UNPROCESSABLE_ENTITY);
        }

        OrderDetailItem orderDetailItem = new OrderDetailItem();
        orderDetailItem.setOrderDetail(orderDetail);
        orderDetailItem.setMenuItem(menuItem);
        orderDetailItem.setAmount(new BigDecimal(request.getAmount()));
        orderDetailItem.setQuantity(Integer.parseInt(request.getQuantity()));
        orderDetailItem.setStatus(StatusEnum.ACTIVE);

        return orderDetailItemRepository.save(orderDetailItem);
    }
}
