package org.maiyon.service.impl;

import lombok.RequiredArgsConstructor;
import org.maiyon.model.dto.request.ProductRequest;
import org.maiyon.model.dto.response.ProductResponse;
import org.maiyon.model.entity.Product;
import org.maiyon.model.enums.ActiveStatus;
import org.maiyon.repository.CategoryRepository;
import org.maiyon.repository.ProductRepository;
import org.maiyon.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public Page<Product> findAllToPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findActiveProductsToPage(Pageable pageable) {
        return productRepository.findByActiveStatus(pageable, ActiveStatus.ACTIVE);
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Optional<Product> findFirstProductName(String productName) {
        return productRepository.findFirstByProductName(productName);
    }
    @Override
    public Optional<Product> save(Product product) {
        return Optional.of(productRepository.save(product));
    }

    @Override
    public Optional<Product> save(ProductRequest productRequest) {
        Product product = entityMap(productRequest);
        return Optional.empty();
    }

    @Override
    public Boolean deleteById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()){
            Product deleteProduct = product.get();
            deleteProduct.setActiveStatus(ActiveStatus.INACTIVE);
            Optional<Product> deletedProduct = save(deleteProduct);
            return deletedProduct.isPresent();
        }
        return false;
    }
    @Override
    public Product entityMap(ProductRequest productRequest) {
        return Product.builder()
                .productId(productRequest.getProductId())
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .unitPrice(productRequest.getUnitPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .image(productRequest.getImageUrl())
                .category(categoryRepository.findById(productRequest.getCategoryId()).orElse(null))
                .activeStatus(productRequest.getActiveStatus() ? ActiveStatus.ACTIVE : ActiveStatus.INACTIVE)
                .build();
    }

    @Override
    public ProductResponse entityMap(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .sku(product.getSku())
                .productName(product.getProductName())
                .description(product.getDescription())
                .unitPrice(product.getUnitPrice())
                .stockQuantity(product.getStockQuantity())
                .imageUrl(product.getImage())
                .category(product.getCategory())
                .build();
    }
}
