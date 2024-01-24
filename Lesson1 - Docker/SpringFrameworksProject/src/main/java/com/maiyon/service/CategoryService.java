package com.maiyon.service;

import com.maiyon.model.dto.request.CategoryRequest;
import com.maiyon.model.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface CategoryService {
    Page<CategoryResponse> findAll(Pageable pageable);
    Optional<CategoryResponse> findById(Long id);
    Optional<CategoryResponse> save(CategoryRequest categoryRequest);
    Boolean delete(Long id);
}