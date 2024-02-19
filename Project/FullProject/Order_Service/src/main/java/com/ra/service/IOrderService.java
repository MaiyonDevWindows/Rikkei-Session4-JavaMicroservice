package com.ra.service;

import com.ra.CustomException;
import com.ra.model.entity.Orders;
import com.ra.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    Page<Orders> getAll(Pageable pageable);
    Orders save(Orders orders);
    Orders findById(Long id) throws CustomException;
    void deleteById(Long id);

    List<Orders> findByProductStatus(OrderStatus status);

    Orders updateStatus(Long orderId, OrderStatus status) throws CustomException;

    Orders findBySerialNumber (String serialNumber);

    Orders cancelOrder(Orders orders) throws CustomException;

    List<Orders> findByUser(Long userId);
}
