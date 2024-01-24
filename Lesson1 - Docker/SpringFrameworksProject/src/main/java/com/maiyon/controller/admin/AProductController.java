package com.maiyon.controller.admin;

import com.maiyon.model.dto.request.ProductRequest;
import com.maiyon.model.dto.response.ProductResponse;
import com.maiyon.model.dto.response.UserResponseToAdmin;
import com.maiyon.service.ProductService;
import jakarta.validation.Valid;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin/products")
public class AProductController {
    @Autowired
    private ProductService productService;
    private final Logger logger =
            LoggerFactory.getLogger(AProductController.class);
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
                logger.info("Admin - Get products at page {} - successful.", page);
                return new ResponseEntity<>(productResponses.getContent(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Products page is empty.", HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            logger.error("Admin - Get products at page {} - failure.", page);
            return new ResponseEntity<>("Products page out of range.", HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") Long productId){
        Optional<ProductResponse> productResponse = productService.findById(productId);
        if(productResponse.isPresent()){
            logger.info("Admin - Get product by id: {} - successful.", productId);
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        }
        logger.error("Admin - Get product by id: {} - failure.", productId);
        return new ResponseEntity<>("Product is not exists.", HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Optional<ProductResponse> productResponse = productService.save(productRequest);
        if(productResponse.isPresent()){
            logger.info("Admin - Create product - successful.");
            return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
        }
        logger.error("Admin - Create product - failure.");
        return new ResponseEntity<>("Can not create product.", HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable("productId") Long productId,
            @RequestBody @Valid ProductRequest productRequest){
        productRequest.setProductId(productId);
        Optional<ProductResponse> productResponse = productService.save(productRequest);
        if(productResponse.isPresent()){
            logger.info("Admin - Update product - successful.");
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        }
        logger.error("Admin - Update product - failure.");
        return new ResponseEntity<>("Can not update product", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Long productId){
        if(productService.delete(productId)){
            logger.info("Admin - Delete product - successful.");
            return new ResponseEntity<>("Delete success.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Delete failure.", HttpStatus.NOT_FOUND);
    }
}