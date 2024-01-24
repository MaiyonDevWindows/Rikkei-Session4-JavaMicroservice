package com.maiyon.service.impl;

import com.maiyon.model.dto.request.WishListRequest;
import com.maiyon.model.dto.response.WishListResponse;
import com.maiyon.model.entity.Product;
import com.maiyon.model.entity.User;
import com.maiyon.model.entity.WishList;
import com.maiyon.repository.UserRepository;
import com.maiyon.repository.WishListRepository;
import com.maiyon.security.user_principal.UserPrincipal;
import com.maiyon.service.ProductService;
import com.maiyon.service.UserService;
import com.maiyon.service.WishListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WishListImpl implements WishListService {
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Page<WishListResponse> findAll(Pageable pageable) {
        List<WishList> wishLists = wishListRepository.findAll();
        List<WishListResponse> wishListResponses = wishLists.stream().map((this::builderWishListResponse)).toList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), wishListResponses.size());
        return PageableExecutionUtils.getPage(wishListResponses.subList(start, end), pageable, wishListResponses::size);
    }

    @Override
    public Optional<WishListResponse> save(WishListRequest wishListRequest) {
        WishList wishList = builderWishList(wishListRequest);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            Optional<User> user = userRepository.findUserByUserId(userPrincipal.getUser().getUserId());
            user.ifPresent(wishList::setUser);
            if(productService.findById(wishListRequest.getProductId()).isPresent()){
                WishList saveWithList = wishListRepository.save(wishList);
                return Optional.ofNullable(builderWishListResponse(saveWithList));
            }
        }
        return Optional.empty();
    }

    @Override
    public Boolean delete(Long id) {
        Optional<WishList> wishList = wishListRepository.findById(id);
        if(wishList.isEmpty())  return false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            Optional<User> user = userRepository.findUserByUserId(userPrincipal.getUser().getUserId());
            if(user.isPresent()){
                if(Objects.equals(user.get().getUserId(), wishList.get().getUser().getUserId())){
                    wishListRepository.deleteById(id);
                    logger.info("User with user id {} just remove product {} from wish list.",
                            user.get().getUserId(), wishList.get().getProduct());
                    return true;
                }
                logger.error("wish list is not owned by user.");
            }
            logger.error("User is not found");
        }
        logger.error("Can not get user data.");
        return false;
    }
    public WishListResponse builderWishListResponse(WishList wishList){
        return WishListResponse.builder()
                .productName(wishList.getProduct().getProductName())
                .build();
    }
    public WishList builderWishList(WishListRequest wishListRequest) {
        Product product = productService.getProductById(wishListRequest.getProductId());
            return WishList.builder()
                .product(productService.getProductById(wishListRequest.getProductId()))
                .build();
    }
}
