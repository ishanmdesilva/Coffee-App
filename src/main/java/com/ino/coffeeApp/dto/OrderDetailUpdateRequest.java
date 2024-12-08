package com.ino.coffeeApp.dto;

import java.util.List;

public class OrderDetailUpdateRequest {

    private String totalAmount;
    private String customerId;
    private List<MenuItemAddRequest> menuItems;

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
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
