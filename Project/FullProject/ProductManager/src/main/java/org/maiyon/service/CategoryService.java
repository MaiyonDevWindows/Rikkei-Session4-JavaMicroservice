package org.maiyon.service;

import org.maiyon.CustomException;
import org.maiyon.model.dto.request.CategoryRequest;
import org.maiyon.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface CategoryService {
    Page<Category> findAllToPage(Pageable pageable);
    Page<Category> findActiveCategoriesToPage(Pageable pageable);
    Optional<Category> findById(Long categoryId);
    Optional<Category> findFirstByCategoryName(String categoryName);
    Optional<Category> save(Category category) throws CustomException;
    Optional<Category> save(CategoryRequest categoryRequest) throws CustomException;
    Boolean deleteById(Long categoryId);

}
