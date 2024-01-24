package com.maiyon.controller.user;

import com.maiyon.model.dto.request.WishListRequest;
import com.maiyon.model.dto.response.WishListResponse;
import com.maiyon.service.WishListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/user/wish-list")
public class WishListController {
    @Autowired
    private WishListService wishListService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping
    public ResponseEntity<?> getAllProductWishList(
            @RequestParam(defaultValue = "5", name = "limit") Integer limit,
            @RequestParam(defaultValue = "0", name = "page") Integer page,
            @RequestParam(defaultValue = "wishListId", name = "sort") String sortBy,
            @RequestParam(defaultValue = "asc", name = "direction") String orderBy){
        Pageable pageable = PageRequest.of(page, limit, Sort.by(orderBy, sortBy));
        try{
            Page<WishListResponse> wishListResponses = wishListService.findAll(pageable);
            if(!wishListResponses.isEmpty()){
                logger.info("User - Get own wishlist at page {} - successful.", page);
                return new ResponseEntity<>(wishListResponses.getContent(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Wishlist page is empty.", HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            logger.error("Admin - Get own wishlist at page {} - failure.", page);
            return new ResponseEntity<>("Wishlist page out of range.", HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping
    public ResponseEntity<?> addProductToWishList(@RequestBody WishListRequest wishListRequest) {
        Optional<WishListResponse> wishListResponse = wishListService.save(wishListRequest);
        if(wishListResponse.isPresent()){
            logger.info("User - Add wishlist - successful.");
            return new ResponseEntity<>(wishListResponse, HttpStatus.CREATED);
        }
        logger.error("User - Add wishlist - failure.");
        return new ResponseEntity<>("Can not add wishlist.", HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{wishListId}")
    public ResponseEntity<?> deleteProductWishList(@PathVariable() Long wishListId){
        if(wishListService.delete(wishListId)){
            logger.info("User - Remove product from wishlist - successful.");
            return new ResponseEntity<>("Remove success.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Remove failure.", HttpStatus.NOT_FOUND);
    }
}