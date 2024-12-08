package com.ino.coffeeApp.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderDetailCreateRequest {

    private String totalAmount;
    private String shopId;
    private String customerId;
    private List<MenuItemAddRequest> menuItems;

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<MenuItemAddRequest> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItemAddRequest> menuItems) {
        this.menuItems = menuItems;
    }
}
