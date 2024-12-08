package com.ino.coffeeApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ino.coffeeApp.enums.StatusEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "queue_detail")
public class QueueDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "queue_name", nullable = false)
    private String queueName;

    @Column(name = "size", nullable = false)
    private Integer size;

    @Column(name = "available_count", nullable = false)
    private Integer availableCount;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEnum status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name="shop_id", nullable = false)
    private ShopDetail shopDetail;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Transient
    private Long shopId;

    public Long getShopId() {
        return shopDetail != null ? shopDetail.getId() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(Integer availableCount) {
        this.availableCount = availableCount;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public ShopDetail getShopDetail() {
        return shopDetail;
    }

    public void setShopDetail(ShopDetail shopDetail) {
        this.shopDetail = shopDetail;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
