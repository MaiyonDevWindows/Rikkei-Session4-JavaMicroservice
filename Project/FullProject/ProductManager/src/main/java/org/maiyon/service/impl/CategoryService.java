package org.maiyon.service.impl;

import lombok.RequiredArgsConstructor;
import org.maiyon.CustomException;
import org.maiyon.model.dto.request.CategoryRequest;
import org.maiyon.model.entity.Category;
import org.maiyon.model.enums.ActiveStatus;
import org.maiyon.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService implements org.maiyon.service.CategoryService {
    private final CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Page<Category> findAllToPage(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Page<Category> findActiveCategoriesToPage(Pageable pageable) {
        return categoryRepository.findByActiveStatus(pageable, ActiveStatus.ACTIVE);
    }

    @Override
    public Optional<Category> findById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Optional<Category> findFirstByCategoryName(String categoryName) {
        return categoryRepository.findFirstByCategoryName(categoryName);
    }

    @Override
    public Optional<Category> save(Category category) {
        return Optional.of(categoryRepository.save(category));
    }

    @Override
    public Optional<Category> save(CategoryRequest categoryRequest) throws CustomException {
        Category category = CategoryRequest.entityMap(categoryRequest);
        if(categoryRepository.findFirstByCategoryName(categoryRequest.getCategoryName()).isPresent())
            throw new CustomException("Category name is already exists.");
        return Optional.of(categoryRepository.save(category));
    }

    @Override
    public Boolean deleteById(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            Category deleteCategory = category.get();
            deleteCategory.setActiveStatus(ActiveStatus.INACTIVE);
            Optional<Category> deletedCategory = save(deleteCategory);
            return deletedCategory.isPresent();
        }
        return false;
    }
}
