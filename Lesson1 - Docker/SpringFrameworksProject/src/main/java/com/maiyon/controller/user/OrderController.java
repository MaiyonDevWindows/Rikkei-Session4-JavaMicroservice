package com.maiyon.controller.user;

import com.maiyon.model.dto.response.OrderDetailResponseToUser;
import com.maiyon.model.dto.response.OrderResponseToUser;
import com.maiyon.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user/history")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAllByUser(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sort));
        Page<OrderResponseToUser> orderResponseToUsers = orderService.getAllByUser(pageable);
        return new ResponseEntity<>(orderResponseToUsers, HttpStatus.OK);
    }

    @GetMapping("/serial={serial}")
    public ResponseEntity<?> getOrderDetailBySerial(@PathVariable String serial){
        List<OrderDetailResponseToUser> ordersResponseToUserDetails = orderService.getBySerialNumber(serial);
        return new ResponseEntity<>(ordersResponseToUserDetails,HttpStatus.OK);
    }

    @GetMapping("/orderStatus={orderStatus}")
    public ResponseEntity<?> getOrderDetailByStatus(@PathVariable String orderStatus){
        List<OrderResponseToUser> ordersResponseToUserDetails = orderService.getByStatusUser(orderStatus);
        return new ResponseEntity<>(ordersResponseToUserDetails,HttpStatus.OK);
    }

    @GetMapping("/cancel/{id}")
    public ResponseEntity<OrderResponseToUser> updateStatusToCancel(@PathVariable Long id){
        OrderResponseToUser ordersResponseToUser = orderService.updateOrderStatusWaitingToCancel(id);
        return new ResponseEntity<>(ordersResponseToUser,HttpStatus.OK);
    }
}
