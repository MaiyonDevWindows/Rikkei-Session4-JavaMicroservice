package org.maiyon.service;

import org.maiyon.CustomException;
import org.maiyon.model.dto.request.ProductRequest;
import org.maiyon.model.dto.response.ProductResponse;
import org.maiyon.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAllToList();
    Page<Product> findAllToPage(Pageable pageable);
    Page<Product> findActiveProductsToPage(Pageable pageable);
    Optional<Product> findById(Long productId);
    Optional<Product> findFirstProductName(String productName);
    Optional<Product> save(Product product);
    Optional<Product> save(ProductRequest productRequest) throws CustomException;
    Boolean deleteById(Long productId);
    Product entityMap(ProductRequest productRequest);
    ProductResponse entityMap(Product product);
}