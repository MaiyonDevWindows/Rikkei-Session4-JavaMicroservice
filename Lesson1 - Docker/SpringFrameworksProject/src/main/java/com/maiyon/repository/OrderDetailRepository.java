package com.maiyon.repository;

import com.maiyon.model.entity.Order;
import com.maiyon.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    default List<OrderDetail> findByOrder(Order order){
        return new ArrayList<>();
    };
}