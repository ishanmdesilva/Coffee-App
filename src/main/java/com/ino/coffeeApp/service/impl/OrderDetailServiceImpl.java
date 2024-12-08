package com.ino.coffeeApp.service.impl;

import com.ino.coffeeApp.dto.MenuItemAddRequest;
import com.ino.coffeeApp.dto.OrderDetailCreateRequest;
import com.ino.coffeeApp.dto.OrderDetailUpdateRequest;
import com.ino.coffeeApp.entity.*;
import com.ino.coffeeApp.enums.StatusEnum;
import com.ino.coffeeApp.exception.ValidateRecordException;
import com.ino.coffeeApp.repository.OrderDetailItemRepository;
import com.ino.coffeeApp.repository.OrderDetailRepository;
import com.ino.coffeeApp.repository.ShopDetailRepository;
import com.ino.coffeeApp.service.OrderDetailItemService;
import com.ino.coffeeApp.service.OrderDetailService;
import com.ino.coffeeApp.service.QueueDetailService;
import com.ino.coffeeApp.service.UserService;
import com.ino.coffeeApp.util.ErrorCodes;
import jakarta.transaction.RollbackException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional(rollbackFor = Exception.class)
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final ShopDetailRepository shopDetailRepository;
    private final QueueDetailService queueDetailService;
    private final UserService userService;
    private final OrderDetailItemService orderDetailItemService;
    private final OrderDetailItemRepository orderDetailItemRepository;

    @Autowired
    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository,
                                  ShopDetailRepository shopDetailRepository,
                                  QueueDetailService queueDetailService,
                                  OrderDetailItemService orderDetailItemService,
                                  OrderDetailItemRepository orderDetailItemRepository,
                                  UserService userService) {
        this.orderDetailRepository = orderDetailRepository;
        this.shopDetailRepository = shopDetailRepository;
        this.queueDetailService = queueDetailService;
        this.orderDetailItemService = orderDetailItemService;
        this.orderDetailItemRepository = orderDetailItemRepository;
        this.userService = userService;
    }

    @Override
    public OrderDetail getOrderById(Long id) {
        Optional<OrderDetail> optOrderDetail = orderDetailRepository.findById(id);
        if(optOrderDetail.isEmpty()) {
            throw new ValidateRecordException("Order detail not found.", ErrorCodes.NO_RECORD_FOUND);
        }
        OrderDetail orderDetail = optOrderDetail.get();

        List<OrderDetailItem> orderDetailItemList = orderDetailItemService.getOrderDetailItemByOrderDetailId(id);
        orderDetail.setOrderDetailItems(orderDetailItemList);

        return orderDetail;
    }

    @Override
    public List<OrderDetail> getOrderByShopId(Long shopDetailId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByShopDetailId(shopDetailId);
        if (orderDetails.isEmpty()) {
            throw new ValidateRecordException("No Orders yet", ErrorCodes.ENTITY_NOT_FOUND);
        }

        for (OrderDetail orderDetail : orderDetails) {
            List<OrderDetailItem> orderDetailItemList = orderDetailItemService.getOrderDetailItemByOrderDetailId(orderDetail.getId());
            orderDetail.setOrderDetailItems(orderDetailItemList);
        }
        return orderDetails;
    }

    @Override
    public OrderDetail saveOrderDetail(OrderDetailCreateRequest request) {
        List<OrderDetailItem> orderDetailItemList = new ArrayList<>();

        // validate shopDetail
        Optional<ShopDetail> optShopDetail = shopDetailRepository.findById(Long.parseLong(request.getShopId()));
        if(optShopDetail.isEmpty()){
            throw new ValidateRecordException("Shop detail not found.", ErrorCodes.ENTITY_NOT_FOUND);
        }
        ShopDetail shopDetail = optShopDetail.get();

        // Get user
        User user = userService.getById(Long.parseLong(request.getCustomerId()));

        // Get queue
        List<QueueDetail> availableQueues = queueDetailService.getAvailableQueues(Long.parseLong(request.getShopId()));
        QueueDetail queueDetail = availableQueues.get(0);

        // Build order detail
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderNo(generatedUniqueValue());
        orderDetail.setStatus(StatusEnum.CONFIRM);
        orderDetail.setQueueDetail(queueDetail);
        orderDetail.setUser(user);
        orderDetail.setTotalAmount(new BigDecimal(request.getTotalAmount()));
        orderDetail.setShopDetail(shopDetail);

        orderDetail = orderDetailRepository.save(orderDetail);

        // Update relevant queue available count
        queueDetailService.updateAvailableQueueSize(queueDetail.getId(), true, queueDetail.getVersion());

        // Save order detail item
        for (MenuItemAddRequest menuItems : request.getMenuItems()) {
            orderDetailItemList.add(orderDetailItemService.saveOrderDetailItem(orderDetail, menuItems));
        }

        orderDetail.setOrderDetailItems(orderDetailItemList);
        return orderDetail;
    }

    @Override
    public OrderDetail updateOrderDetail(Long orderDetailsId, OrderDetailUpdateRequest request) {
        List<OrderDetailItem> orderDetailItemList = new ArrayList<>();
        OrderDetail orderDetail = getOrderById(orderDetailsId);

        // Validate status
        if(!orderDetail.getStatus().equals(StatusEnum.CONFIRM)) {
            throw new ValidateRecordException("Cannot update order", ErrorCodes.UNPROCESSABLE_ENTITY);
        }

        // User validate
        if(!orderDetail.getUserId().equals(Long.parseLong(request.getCustomerId()))) {
            throw new ValidateRecordException("Cannot update different user", ErrorCodes.UNPROCESSABLE_ENTITY);
        }

        orderDetail.setTotalAmount(new BigDecimal(request.getTotalAmount()));
        orderDetail = orderDetailRepository.save(orderDetail);

        // Delete existing order detail item
        orderDetailItemRepository.deleteByOrderDetailId(orderDetail.getId());

        // Save order detail item
        for (MenuItemAddRequest menuItems : request.getMenuItems()) {
            orderDetailItemList.add(orderDetailItemService.saveOrderDetailItem(orderDetail, menuItems));
        }

        orderDetail.setOrderDetailItems(orderDetailItemList);
        return orderDetail;
    }

    @Override
    public OrderDetail orderCancellation(Long id) {
        OrderDetail orderDetail = getOrderById(id);

        // Validate status
        if(!orderDetail.getStatus().equals(StatusEnum.CONFIRM)) {
            throw new ValidateRecordException("Cannot cancelled order", ErrorCodes.UNPROCESSABLE_ENTITY);
        }

        orderDetail.setStatus(StatusEnum.CANCELLED);
        QueueDetail queueDetail = orderDetail.getQueueDetail();

        // Update relevant queue available count
        queueDetailService.updateAvailableQueueSize(queueDetail.getId(), false, queueDetail.getVersion());

        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail orderCompleted(Long id) {
        OrderDetail orderDetail = getOrderById(id);

        // Validate status
        if(!orderDetail.getStatus().equals(StatusEnum.CONFIRM)) {
            throw new ValidateRecordException("Cannot completed order", ErrorCodes.UNPROCESSABLE_ENTITY);
        }

        orderDetail.setStatus(StatusEnum.COMPLETED);
        QueueDetail queueDetail = orderDetail.getQueueDetail();

        // Update relevant queue available count
        queueDetailService.updateAvailableQueueSize(queueDetail.getId(), false, queueDetail.getVersion());

        return orderDetailRepository.save(orderDetail);
    }

    private Long generatedUniqueValue() {
        UUID uuid = UUID.randomUUID();
        return Math.abs(uuid.getMostSignificantBits());
    }
}
