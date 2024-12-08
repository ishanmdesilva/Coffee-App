package com.ino.coffeeApp.controller;

import com.ino.coffeeApp.entity.QueueDetail;
import com.ino.coffeeApp.entity.ShopDetail;
import com.ino.coffeeApp.service.ShopDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/shop-detail")
@RestController
public class ShopDetailController {

    private final ShopDetailService shopDetailService;

    @Autowired
    public ShopDetailController(ShopDetailService shopDetailService) {
        this.shopDetailService = shopDetailService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getShopDetailById(@PathVariable Long id) {
        ShopDetail shopDetail = shopDetailService.getShopDetailById(id);
        return new ResponseEntity<>(shopDetail, HttpStatus.OK);
    }

}
