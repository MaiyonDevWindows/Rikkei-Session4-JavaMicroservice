package com.ra.repository;

import com.ra.model.entity.Orders;
import com.ra.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Long> {
    List<Orders> findAllByStatus(OrderStatus orderStatus);
}
