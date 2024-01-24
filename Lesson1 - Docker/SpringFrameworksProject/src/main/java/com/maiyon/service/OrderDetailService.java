package com.maiyon.service;

import com.maiyon.model.dto.response.OrderDetailResponse;
import com.maiyon.model.entity.Order;
import com.maiyon.model.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    void addOrderDetail(OrderDetail orderDetail);

    OrderDetailResponse entityMap(OrderDetail orderDetail);

    List<OrderDetailResponse> findByOrder(Order order);
}
