package com.ra.service;

import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.Orders;

import java.util.List;

public interface IOrderDetailService {
    public List<OrderDetail> findByOrder(Orders orders);
}
