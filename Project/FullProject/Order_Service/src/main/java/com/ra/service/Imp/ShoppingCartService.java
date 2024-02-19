package com.ra.service.Imp;


import com.ra.CustomException;
import com.ra.model.dto.receive.ProductReceive;
import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.Orders;
import com.ra.model.entity.ShoppingCart;
import com.ra.model.enums.OrderStatus;
import com.ra.repository.OrderDetailRepository;
import com.ra.repository.ShoppingCartRepository;
import com.ra.service.IOrderService;
import com.ra.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ShoppingCartService implements IShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private IOrderService orderService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<ShoppingCart> getAll() throws CustomException {
        return shoppingCartRepository.findAll();
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) throws CustomException {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart findById(Integer id) throws CustomException {
        return shoppingCartRepository.findById(id).orElseThrow(()->new CustomException("There is no shopping cart with this id"));
    }

    @Override
    public Orders checkout(Long userId) throws CustomException {
        List<ShoppingCart> shoppingCartList=shoppingCartRepository.findAllByUserId(userId);
        if(shoppingCartList.isEmpty()){
            throw new CustomException("There are no product found in your shopping cart");
        }
        double totalPrice=0.00;
        for (ShoppingCart shoppingCart : shoppingCartList) {
            ProductReceive productReceive= commonService.findProductById(shoppingCart.getProductId());
            totalPrice = totalPrice + shoppingCart.getQuantity() * productReceive.getUnitPrice();
        }
        Orders orders=Orders.builder()
                .createdAt(new Date())
                .status(OrderStatus.WAITING)
                .totalPrice(totalPrice)
                .userId(userId)
                .build();
        Orders ordersNew=orderService.save(orders);
        List<OrderDetail> orderDetailList=shoppingCartList.stream().map((item)->OrderDetail.builder()
                .orders(ordersNew)
                .orderQuantity(item.getQuantity())
                .unitPrice(commonService.findProductById(item.getProductId()).getUnitPrice())
                .name(commonService.findProductById(item.getProductId()).getProductName())
                .build()).toList();
        orderDetailList.stream().map((item)->orderDetailRepository.save(item)).toList();
        for (ShoppingCart shoppingCart :
                shoppingCartList) {
            shoppingCartRepository.deleteById(shoppingCart.getId());
        }
        return orderService.findById(ordersNew.getId());
    }
}
