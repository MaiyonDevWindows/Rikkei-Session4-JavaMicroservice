package com.maiyon.repository;

import com.maiyon.model.entity.Category;
import com.maiyon.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByCategory(Category category);
    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE %:keyword% OR LOWER(p.description) LIKE %:keyword%")
    List<Product> searchByNameOrDescription(@Param("keyword") String keyword);
}
