package com.ino.coffeeApp.repository;

import com.ino.coffeeApp.entity.ShopDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopDetailRepository extends JpaRepository<ShopDetail, Long> {
}
