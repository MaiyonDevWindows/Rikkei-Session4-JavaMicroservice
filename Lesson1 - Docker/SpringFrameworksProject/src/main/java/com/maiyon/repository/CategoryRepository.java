package com.maiyon.repository;

import com.maiyon.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findCategoriesByCategoryName(String categoryName);
    Category findCategoryByCategoryId(Long categoryId);
}
