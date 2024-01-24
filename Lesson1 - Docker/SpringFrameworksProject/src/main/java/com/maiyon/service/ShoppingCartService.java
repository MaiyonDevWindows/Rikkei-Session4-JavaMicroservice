package com.maiyon.service;

import com.maiyon.model.dto.request.ShoppingCartRequest;
import com.maiyon.model.dto.response.ShoppingCartResponse;
import com.maiyon.model.entity.ShoppingCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShoppingCartService {
    Page<ShoppingCartResponse> findAll(Pageable pageable);

    ShoppingCart save(ShoppingCart shoppingCart);

    void deleteOneProduct(Long id);

    void deleteAllProduct();

    ShoppingCart findById(Integer Id);

//    void checkOut(Long idAddress);
    ShoppingCart entityMap(ShoppingCartRequest shoppingCartRequest);
    ShoppingCartResponse entityMap(ShoppingCart shoppingCart);
}
