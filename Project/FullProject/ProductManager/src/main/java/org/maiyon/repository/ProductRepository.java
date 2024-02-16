package org.maiyon.repository;

import org.maiyon.model.entity.Product;
import org.maiyon.model.enums.ActiveStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByActiveStatus(Pageable pageable, ActiveStatus activeStatus);
    Optional<Product> findFirstByProductName(String productName);
}
