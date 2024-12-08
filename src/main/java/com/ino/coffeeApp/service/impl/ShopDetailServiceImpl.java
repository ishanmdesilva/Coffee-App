package com.ino.coffeeApp.service.impl;

import com.ino.coffeeApp.entity.MenuItem;
import com.ino.coffeeApp.entity.QueueDetail;
import com.ino.coffeeApp.entity.ShopDetail;
import com.ino.coffeeApp.exception.ValidateRecordException;
import com.ino.coffeeApp.repository.MenuItemRepository;
import com.ino.coffeeApp.repository.QueueDetailRepository;
import com.ino.coffeeApp.repository.ShopDetailRepository;
import com.ino.coffeeApp.service.ShopDetailService;
import com.ino.coffeeApp.util.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopDetailServiceImpl implements ShopDetailService {

    private final ShopDetailRepository shopDetailRepository;
    private final QueueDetailRepository queueDetailRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public ShopDetailServiceImpl(ShopDetailRepository shopDetailRepository,
                                 QueueDetailRepository queueDetailRepository,
                                 MenuItemRepository menuItemRepository) {
        this.shopDetailRepository = shopDetailRepository;
        this.queueDetailRepository = queueDetailRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public ShopDetail getShopDetailById(Long shopDetailId) {
        Optional<ShopDetail> optShopDetail = shopDetailRepository.findById(shopDetailId);
        if(optShopDetail.isEmpty()){
            throw new ValidateRecordException("Shop detail not found.", ErrorCodes.ENTITY_NOT_FOUND);
        }
        ShopDetail shopDetail = optShopDetail.get();

        shopDetail.setQueueDetailList(queueDetailRepository.findByShopDetailId(shopDetailId));
        shopDetail.setMenuItemList(menuItemRepository.findByShopDetailId(shopDetailId));

        return shopDetail;
    }
}
