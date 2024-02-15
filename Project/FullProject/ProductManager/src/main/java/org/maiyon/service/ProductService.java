package org.maiyon.service;

import org.maiyon.model.entity.Category;
import org.maiyon.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Page<Product> findAllByPage(Pageable pageable);
    Optional<Category> findById(Long categoryId);
    Optional<Category> save(Category category);
    Boolean delete(Long categoryId);
}