package com.maiyon.service.impl;

import com.maiyon.model.dto.request.ProductRequest;
import com.maiyon.model.dto.response.ProductResponse;
import com.maiyon.model.entity.Category;
import com.maiyon.model.entity.Product;
import com.maiyon.repository.CategoryRepository;
import com.maiyon.repository.ProductRepository;
import com.maiyon.service.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private final ModelMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    ProductServiceImpl(){
        this.mapper = new ModelMapper();
    }
    private void configureModelMapper(){
        mapper.addMappings(new PropertyMap<ProductRequest, Product>() {
            @Override
            protected void configure() {
                map().setProductId(source.getProductId());
                map().setProductName(source.getProductName());
                map().setDescription(source.getDescription());
                map().setUnitPrice(source.getUnitPrice());
                map().setStockQuantity(source.getStockQuantity());
                map().setImage(source.getImageUrl());
                map().setCategory(categoryRepository.findCategoryByCategoryId(source.getCategoryId()));
            }
        });
        mapper.addMappings(new PropertyMap<Product, ProductResponse>() {
            @Override
            protected void configure() {
                map().setId(source.getProductId());
                map().setProductName(source.getProductName());
                map().setDescription(source.getDescription());
                map().setUnitPrice(source.getUnitPrice());
                map().setStockQuantity(source.getStockQuantity());
                map().setImageUrl(source.getImage());
                map().setCategory(source.getCategory());
            }
        });
    }
    @Override
    public Page<ProductResponse> findAll(Pageable pageable) {
        List<Product> products = productRepository.findAll();
        return convertListToPageProductResponses(pageable, products);
    }

    @Override
    public Optional<ProductResponse> findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return Optional.ofNullable(mapper.map(product, ProductResponse.class));
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    @Override
    public Page<ProductResponse> findByCategoryId(Pageable pageable, Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            List<Product> products = productRepository.findProductsByCategory(category.get());
            return convertListToPageProductResponses(pageable, products);
        }
        logger.error("Can not found category by id {}.", categoryId);
        return Page.empty();
    }

    private Page<ProductResponse> convertListToPageProductResponses(Pageable pageable, List<Product> products) {
        List<ProductResponse> productResponses = products.stream().map(this::entityMap).toList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), productResponses.size());
        return PageableExecutionUtils.getPage(productResponses.subList(start, end), pageable, productResponses::size);
    }

    @Override
    public Page<ProductResponse> searchByNameOrDescription(Pageable pageable, String keyword) {
        List<Product> products = productRepository.searchByNameOrDescription(keyword);
        return convertListToPageProductResponses(pageable, products);
    }

    @Override
    public Optional<ProductResponse> save(ProductRequest productRequest) {
        Product updateProduct = mapper.map(productRequest, Product.class);
        Product updatedProduct = productRepository.save(updateProduct);
        return Optional.ofNullable(mapper.map(updatedProduct, ProductResponse.class));
    }

    @Override
    public Boolean delete(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()) return false;
        productRepository.deleteById(id);
        return true;
    }
    @Override
    public Product entityMap(ProductRequest productRequest){
        return Product.builder()
                .productId(productRequest.getProductId())
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .unitPrice(productRequest.getUnitPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .image(productRequest.getImageUrl())
                .category(categoryRepository.findById(productRequest.getCategoryId()).orElse(null))
                .build();
    }
    @Override
    public ProductResponse entityMap(Product product){
        return ProductResponse.builder()
            .id(product.getProductId())
            .productName(product.getProductName())
            .description(product.getDescription())
            .unitPrice(product.getUnitPrice())
            .stockQuantity(product.getStockQuantity())
            .imageUrl(product.getImage())
            .category(product.getCategory())
            .build();
    }
}
