package com.ino.coffeeApp.repository;

import com.ino.coffeeApp.entity.OrderDetailItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailItemRepository extends JpaRepository<OrderDetailItem, Long> {

    List<OrderDetailItem> findByOrderDetailId(Long id);

    void deleteByOrderDetailId(Long orderDetailId);

}
