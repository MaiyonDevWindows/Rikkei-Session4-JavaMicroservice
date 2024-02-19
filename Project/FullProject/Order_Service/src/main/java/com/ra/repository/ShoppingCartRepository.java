package com.ra.repository;

import com.ra.model.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Integer> {
    List<ShoppingCart> findAllByUserId(Long userId);
}
