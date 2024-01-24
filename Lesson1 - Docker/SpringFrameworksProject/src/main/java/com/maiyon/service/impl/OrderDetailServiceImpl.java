package com.maiyon.service.impl;

import com.maiyon.model.dto.response.OrderDetailResponse;
import com.maiyon.model.entity.Order;
import com.maiyon.model.entity.OrderDetail;
import com.maiyon.repository.OrderDetailRepository;
import com.maiyon.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Override
    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }
    @Override
    public List<OrderDetailResponse> findByOrder(Order orders){
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(orders);
        return orderDetails.stream().map(this::entityMap).toList();
    }

    @Override
    public OrderDetailResponse entityMap(OrderDetail orderDetail){
        return OrderDetailResponse.builder()
            .name(orderDetail.getOrderDetailName())
            .unitPrice(orderDetail.getUnitPrice())
            .orderQuantity(orderDetail.getOrderQuantity())
            .build();
    }
}
