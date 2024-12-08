package com.ino.coffeeApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ino.coffeeApp.enums.StatusEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "order_no", nullable = false, unique = true)
    private Long orderNo;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEnum status;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name="shop_id", nullable = false)
    private ShopDetail shopDetail;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name="customer_id", nullable = false)
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name="queue_id", nullable = false)
    private QueueDetail queueDetail;

    @Transient
    private Long shopDetailIdValue;

    @Transient
    private Long userId;

    @Transient
    private Long queueDetailId;

    @Transient
    private String queueName;

    @Transient
    private List<OrderDetailItem> orderDetailItems;

    public Long getShopDetailIdValue() {
        return shopDetail.getId();
    }

    public Long getUserId() {
        return user.getId();
    }

    public Long getQueueDetailId() {
        return queueDetail.getId();
    }

    public String getQueueName() {
        return queueDetail.getQueueName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ShopDetail getShopDetail() {
        return shopDetail;
    }

    public void setShopDetail(ShopDetail shopDetail) {
        this.shopDetail = shopDetail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public QueueDetail getQueueDetail() {
        return queueDetail;
    }

    public void setQueueDetail(QueueDetail queueDetail) {
        this.queueDetail = queueDetail;
    }

    public List<OrderDetailItem> getOrderDetailItems() {
        return orderDetailItems;
    }

    public void setOrderDetailItems(List<OrderDetailItem> orderDetailItems) {
        this.orderDetailItems = orderDetailItems;
    }
}
