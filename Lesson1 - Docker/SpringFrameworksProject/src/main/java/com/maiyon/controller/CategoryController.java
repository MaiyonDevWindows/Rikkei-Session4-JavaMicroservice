package com.maiyon.controller;

import com.maiyon.model.dto.response.CategoryResponse;
import com.maiyon.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    private final Logger logger =
            LoggerFactory.getLogger(CategoryController.class);
    @GetMapping
    public ResponseEntity<?> getAllCategories(
            @RequestParam(defaultValue = "5",name = "limit") Integer limit,
            @RequestParam(defaultValue = "0",name = "page") Integer page,
            @RequestParam(defaultValue = "categoryName",name = "sort") String sortBy,
            @RequestParam(defaultValue = "asc", name = "direction") String orderBy){
        Pageable pageable = PageRequest.of(page, limit, Sort.by(orderBy, sortBy));
        try{
            Page<CategoryResponse> categoryResponses = categoryService.findAll(pageable);
            if(!categoryResponses.isEmpty()){
                logger.info("Any - Get categories at page {} - successful.", page);
                return new ResponseEntity<>(categoryResponses.getContent(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Categories page is empty.", HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("Any - Get categories at page {} - failure", page);
            return new ResponseEntity<>("Categories page out of range.", HttpStatus.UNAUTHORIZED);
        }
    }
}
