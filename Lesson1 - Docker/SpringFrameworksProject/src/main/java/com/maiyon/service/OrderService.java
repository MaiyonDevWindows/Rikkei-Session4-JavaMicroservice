package com.maiyon.service;

import com.maiyon.model.dto.request.OrderStatusRequest;
import com.maiyon.model.dto.response.OrderDetailResponseToUser;
import com.maiyon.model.dto.response.OrderResponseToAdmin;
import com.maiyon.model.dto.response.OrderResponseToUser;
import com.maiyon.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Page<OrderResponseToAdmin> findAll(Pageable pageable);
    void addOrders(Order orders);
    Page<OrderResponseToUser> getAllByUser(Pageable pageable);
    List<OrderDetailResponseToUser> getBySerialNumber(String serial);
    List<OrderResponseToUser> getByStatusUser(String status);
    List<OrderResponseToAdmin> getByStatusAdmin(String status);
    OrderDetailResponseToUser getOrderDetailAdminById(Long id);
    Order updateStatusOrders(Long id, OrderStatusRequest orderStatusRequest);
    OrderResponseToUser updateOrderStatusWaitingToCancel(Long id);
}
