package org.maiyon.service;

import org.maiyon.model.dto.request.ProductRequest;
import org.maiyon.model.dto.response.ProductResponse;
import org.maiyon.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface ProductService {
    Page<Product> findAllToPage(Pageable pageable);
    Page<Product> findActiveProductsToPage(Pageable pageable);
    Optional<Product> findById(Long productId);
    Optional<Product> findFirstProductName(String productName);
    Optional<Product> save(Product product);
    Optional<Product> save(ProductRequest productRequest);
    Boolean deleteById(Long productId);
    Product entityMap(ProductRequest productRequest);
    ProductResponse entityMap(Product product);
}