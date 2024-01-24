package com.maiyon.controller;

import com.maiyon.model.dto.response.ProductResponse;
import com.maiyon.service.ProductService;
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
@RequestMapping("/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    private final Logger logger =
            LoggerFactory.getLogger(ProductController.class);
    @GetMapping
    public ResponseEntity<?> getAllProducts(
            @RequestParam(defaultValue = "5", name = "limit") Integer limit,
            @RequestParam(defaultValue = "0", name = "page") Integer page,
            @RequestParam(defaultValue = "productName", name = "sort") String sortBy,
            @RequestParam(defaultValue = "asc", name = "direction") String orderBy){
        Pageable pageable = PageRequest.of(page, limit, Sort.by(orderBy, sortBy));
        try{
            Page<ProductResponse> productResponses = productService.findAll(pageable);
            if(!productResponses.isEmpty()){
                logger.info("Any - Get products at page {} - successful.", page);
                return new ResponseEntity<>(productResponses.getContent(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Products page is empty.", HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            logger.error("Any - Get products at page {} - failure.", page);
            return new ResponseEntity<>("Products page out of range.", HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") Long productId){
        Optional<ProductResponse> productResponse = productService.findById(productId);
        if(productResponse.isPresent()){
            logger.info("Any - Get product by id: {} - successful.", productId);
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        }
        logger.error("Any - Get product by id: {} - failure.", productId);
        return new ResponseEntity<>("Product is not exists.", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchUserByUsernameAndFullName(
            @PathVariable("keyword") String keyword,
            @RequestParam(defaultValue = "5", name = "limit") Integer limit,
            @RequestParam(defaultValue = "0", name = "page") Integer page,
            @RequestParam(defaultValue = "productName", name = "sort") String sortBy,
            @RequestParam(defaultValue = "asc", name = "direction") String orderBy){
        Pageable pageable = PageRequest.of(page, limit, Sort.by(orderBy, sortBy));
        try{
            Page<ProductResponse> productResponses = productService.searchByNameOrDescription(pageable, keyword);
            if(!productResponses.isEmpty()){
                logger.info("Admin - Search products with keyword {} at page {} - successful.", keyword, page);
                return new ResponseEntity<>(productResponses.getContent(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Products page is empty.", HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            logger.error("Admin - Search products with keyword {} at page {} - failure.", keyword, page);
            return new ResponseEntity<>("Products page out of range.", HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<?> searchUserByUsernameAndFullName(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam(defaultValue = "5", name = "limit") Integer limit,
            @RequestParam(defaultValue = "0", name = "page") Integer page,
            @RequestParam(defaultValue = "productName", name = "sort") String sortBy,
            @RequestParam(defaultValue = "asc", name = "direction") String orderBy){
        Pageable pageable = PageRequest.of(page, limit, Sort.by(orderBy, sortBy));
        try{
            Page<ProductResponse> productResponses = productService.findByCategoryId(pageable, categoryId);
            if(!productResponses.isEmpty()){
                logger.info("Any - Get all products with categoryId {} at page {} - successful.", categoryId, page);
                return new ResponseEntity<>(productResponses.getContent(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Products page is empty.", HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            logger.error("Any - Get all products with categoryId {} at page {} - failure.", categoryId, page);
            return new ResponseEntity<>("Products page out of range.", HttpStatus.UNAUTHORIZED);
        }
    }
}
