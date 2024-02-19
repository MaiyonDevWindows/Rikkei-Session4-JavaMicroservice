package com.ra.service;

import com.ra.CustomException;
import com.ra.model.entity.Orders;
import com.ra.model.entity.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IShoppingCartService {
    List<ShoppingCart> getAll() throws CustomException;
    ShoppingCart save(ShoppingCart shoppingCart) throws CustomException;
    ShoppingCart findById(Integer id) throws CustomException;
    Orders checkout(Long userId) throws CustomException;

}
