package com.maiyon.service.impl;

import com.maiyon.model.dto.request.OrderStatusRequest;
import com.maiyon.model.dto.response.OrderDetailResponse;
import com.maiyon.model.dto.response.OrderDetailResponseToUser;
import com.maiyon.model.dto.response.OrderResponseToAdmin;
import com.maiyon.model.dto.response.OrderResponseToUser;
import com.maiyon.model.entity.Order;
import com.maiyon.model.entity.User;
import com.maiyon.model.entity.enums.OrderStatus;
import com.maiyon.repository.OrdersRepository;
import com.maiyon.security.user_principal.UserPrincipal;
import com.maiyon.service.OrderDetailService;
import com.maiyon.service.OrderService;
import com.maiyon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDetailService orderDetailService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Page<OrderResponseToAdmin> findAll(Pageable pageable) {
        Page<Order> orders = ordersRepository.findAll(pageable);
        return orders.map(this::entityMapOrderResponseToAdmin);
    }

    @Override
    public void addOrders(Order orders) {
        ordersRepository.save(orders);
    }

    @Override
    public Page<OrderResponseToUser> getAllByUser(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            User user = userPrincipal.getUser();
            Page<Order> orders = ordersRepository.findAllByUser(user, pageable);
            return orders.map(this::entityMapOrderResponseToUser);
        }
        return Page.empty();
    }

    @Override
    public List<OrderDetailResponseToUser> getBySerialNumber(String serial) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            User user = userPrincipal.getUser();
            List<Order> orders = ordersRepository.findAllBySerialNumberContainingIgnoreCase(serial, user.getUserId());
            return orders.stream()
                    .map(this::entityMapOrdersResponseUserDetail)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<OrderResponseToUser> getByStatusUser(String status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            User user = userPrincipal.getUser();
            List<Order> orders =
                    ordersRepository.findAllByStatusOrdersContainingIgnoreCaseForUser(status, user.getUserId());
            return orders.stream()
                    .map(this::entityMapOrderResponseToUser)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<OrderResponseToAdmin> getByStatusAdmin(String status) {
        List<Order> orders = ordersRepository.findAllByStatusOrdersContainingIgnoreCase(status);
        if (orders.isEmpty()) {
            logger.error("Status not found");
            throw new RuntimeException();
        }
        return orders.stream()
                .map(this::entityMapOrderResponseToAdmin)
                .collect(Collectors.toList());
    }

    public Order findById(Long id) {
        return ordersRepository.findById(id).orElse(null);
    }

    @Override
    public OrderDetailResponseToUser getOrderDetailAdminById(Long id) {
        Order orders = findById(id);
        if (orders == null) {
            logger.error("ID not found");
            throw new RuntimeException();
        }
        return entityMapOrdersResponseUserDetail(orders);
    }

    @Override
    public Order updateStatusOrders(Long id, OrderStatusRequest orderStatusRequest) {
        Order orders = findById(id);
        if (orders == null) {
            logger.error("ID not found");
            throw new RuntimeException();
        }
        boolean check = false;
        OrderStatus sto = null;
        for (OrderStatus statusOrders : OrderStatus.values()) {
            if (statusOrders.toString().equalsIgnoreCase(orderStatusRequest.getStatusOrders())) {
                check = true;
                sto = statusOrders;
            }
        }
        if (!check) {
            logger.error("Status not true");
            throw new RuntimeException();
        }
        orders.setOrderStatus(sto);
        return ordersRepository.save(orders);
    }

    @Override
    public OrderResponseToUser updateOrderStatusWaitingToCancel(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            User user = userPrincipal.getUser();
            Order orders = ordersRepository.findByOrderIdAndUserAndOrderStatus(id, user, OrderStatus.WAITING);
            orders.setOrderStatus(OrderStatus.CANCEL);
            Order ordersNew = ordersRepository.save(orders);
            return entityMapOrderResponseToUser(ordersNew);
        }
        return null;
    }

    public OrderResponseToAdmin entityMapOrderResponseToAdmin(Order orders) {
        return OrderResponseToAdmin.builder()
                .serialNumber(orders.getSerialNumber())
                .totalPrice(orders.getTotalPrice())
                .orderStatus(orders.getOrderStatus())
                .note(orders.getOrderNote())
                .receiveName(orders.getReceiveName())
                .receiveAddress(orders.getReceiveAddress())
                .receivePhone(orders.getReceivePhone())
                .receivedAt(orders.getReceivedAt())
                .userId(orders.getUser().getUserId())
                .build();
    }
    public OrderResponseToUser entityMapOrderResponseToUser(Order orders) {
        return OrderResponseToUser.builder()
                .serialNumber(orders.getSerialNumber())
                .totalPrice(orders.getTotalPrice())
                .orderStatus(orders.getOrderStatus())
                .note(orders.getOrderNote())
                .build();
    }
    public OrderDetailResponseToUser entityMapOrdersResponseUserDetail(Order orders) {
        List<OrderDetailResponse> orderDetailResponses = orderDetailService.findByOrder(orders);
        return OrderDetailResponseToUser.builder()
                .serialNumber(orders.getSerialNumber())
                .totalPrice(orders.getTotalPrice())
                .orderStatus(orders.getOrderStatus())
                .note(orders.getOrderNote())
                .receiveName(orders.getReceiveName())
                .receiveAddress(orders.getReceiveAddress())
                .receivePhone(orders.getReceivePhone())
                .createdAt(orders.getCreatedAt())
                .receivedAt(orders.getReceivedAt())
                .orderDetailResponses(orderDetailResponses)
                .build();
    }
}
