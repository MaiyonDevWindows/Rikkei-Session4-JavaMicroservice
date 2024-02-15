package org.maiyon.repository;

import org.maiyon.model.entity.Category;
import org.maiyon.model.enums.ActiveStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByActiveStatus(Pageable pageable, ActiveStatus activeStatus);
    Optional<Category> findFirstByCategoryName(String categoryName);
}
