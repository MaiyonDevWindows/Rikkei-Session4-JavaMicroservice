package com.ra.service.Imp;

import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.Orders;
import com.ra.repository.OrderDetailRepository;
import com.ra.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService implements IOrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Override
    public List<OrderDetail> findByOrder(Orders orders) {
        return orderDetailRepository.findAllByOrders(orders);
    }
}
