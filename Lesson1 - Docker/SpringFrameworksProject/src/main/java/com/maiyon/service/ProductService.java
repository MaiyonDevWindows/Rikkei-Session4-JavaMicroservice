package com.maiyon.service;

import com.maiyon.model.dto.request.ProductRequest;
import com.maiyon.model.dto.response.ProductResponse;
import com.maiyon.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Page<ProductResponse> findAll(Pageable pageable);
    Optional<ProductResponse> findById(Long id);
    Product getProductById(Long id);
    Page<ProductResponse> findByCategoryId(Pageable pageable, Long categoryId);
    Page<ProductResponse> searchByNameOrDescription(Pageable pageable, String keyword);
    Optional<ProductResponse> save(ProductRequest productRequest);
    Boolean delete(Long id);
    Product entityMap(ProductRequest productRequest);
    ProductResponse entityMap(Product product);
}
