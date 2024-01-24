package com.maiyon.service.impl;

import com.maiyon.model.dto.request.ShoppingCartRequest;
import com.maiyon.model.dto.response.ShoppingCartResponse;
import com.maiyon.model.entity.Product;
import com.maiyon.model.entity.ShoppingCart;
import com.maiyon.model.entity.User;
import com.maiyon.repository.ShoppingCartRepository;
import com.maiyon.security.user_principal.UserPrincipal;
import com.maiyon.service.ProductService;
import com.maiyon.service.ShoppingCartService;
import com.maiyon.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Page<ShoppingCartResponse> findAll(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            User user = userPrincipal.getUser();
            Page<ShoppingCart> shoppingCarts = shoppingCartRepository.getWithUserId(user.getUserId(), pageable);
            return shoppingCarts.map(this::entityMap);
        }
        return Page.empty();
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void deleteOneProduct(Long id) {
        shoppingCartRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAllProduct() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            User user = userPrincipal.getUser();
            shoppingCartRepository.deleteByUser(user);
        }
    }

    @Override
    public ShoppingCart findById(Integer Id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            User user = userPrincipal.getUser();
            return shoppingCartRepository.findByShoppingCartIdAndUser(Id, user);
        }
        return null;
    }
//    @Transactional
//    @Override
//    public void checkOut(Long idAddress) {
//        Address address = addressService.findAddressById(idAddress);
//        if (address==null){
//            logger.error("ID Address not found in this User");
//            throw new RuntimeException();
//        }
//        Orders orders = new Orders();
//        orders.setSerialNumber(UUID.randomUUID().toString());
//        orders.setStatusOrders(StatusOrders.WAITING);
//        orders.setNote("");
//        orders.setCreatedAt(LocalDate.now());
//        orders.setReceiveName(address.getReceiveName());
//        orders.setReceiveAddress(address.getFullAddress());
//        orders.setReceivePhone(address.getPhone());
//        orders.setReceivedAt(LocalDate.now().plusDays(4));
//        List<Shopping_Cart> shoppingCarts = shoppingCartRepository.findAllByUser(userService.userLogin());
//        double sum = 0;
//        for (Shopping_Cart cart: shoppingCarts) {
//            sum+=cart.getOrderQuantity()*cart.getProduct().getUnitPrice();
//            Order_Detail order_detail = new Order_Detail();
//            order_detail.setName(cart.getProduct().getProductName());
//            order_detail.setOrder(orders);
//            order_detail.setOrderQuantity(cart.getOrderQuantity());
//            order_detail.setUnitPrice(cart.getProduct().getUnitPrice());
//            order_detail.setProduct(productService.findById(cart.getProduct().getId()));
//            orderDetailService.addOrderDetail(order_detail);
//        }
//        orders.setTotalPrice(sum);
//        orders.setUser(userService.userLogin());
//        ordersService.addOrders(orders);
//        deleteAllProduct();
//    }
    @Override
    public ShoppingCart entityMap(ShoppingCartRequest shoppingCartRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            User user = userPrincipal.getUser();
            Optional<Product> product = Optional.ofNullable(productService.getProductById(shoppingCartRequest.getProductId()));
            if (product.isPresent()) {
                return ShoppingCart.builder().
                        orderQuantity(shoppingCartRequest.getOrderQuantity())
                        .product(product.get())
                        .user(user)
                        .build();
            }
        }
        return null;
    }
    @Override
    public ShoppingCartResponse entityMap(ShoppingCart shoppingCart) {
        return ShoppingCartResponse.builder()
            .productName(shoppingCart.getProduct().getProductName())
            .unitPrice(shoppingCart.getProduct().getUnitPrice())
            .orderQuantity(shoppingCart.getOrderQuantity())
            .build();
    }
}