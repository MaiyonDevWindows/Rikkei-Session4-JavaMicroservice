package com.ra.service.Imp;

import com.ra.CustomException;
import com.ra.model.entity.Orders;
import com.ra.model.enums.OrderStatus;
import com.ra.repository.OrderRepository;
import com.ra.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public Page<Orders> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Orders save(Orders orders) {
        return orderRepository.save(orders);
    }

    @Override
    public Orders findById(Long id) throws CustomException {
        return orderRepository.findById(id).orElseThrow(()->new CustomException("There are no repository for this id"));
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Orders> findByProductStatus(OrderStatus status) {
        return orderRepository.findAllByStatus(status);
    }

    @Override
    public Orders updateStatus(Long orderId, OrderStatus status) throws CustomException {
        Orders orders=findById(orderId);
        orders.setStatus(status);
        return orderRepository.save(orders);
    }

    @Override
    public Orders findBySerialNumber(String serialNumber) {
        return null;
    }

    @Override
    public Orders cancelOrder(Orders orders) throws CustomException {
        orders.setStatus(OrderStatus.CANCEL);
        return orderRepository.save(orders);
    }

    @Override
    public List<Orders> findByUser(Long userId) {
        return null;
    }
}
