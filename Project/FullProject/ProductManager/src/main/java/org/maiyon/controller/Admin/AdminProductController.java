package org.maiyon.controller.Admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.maiyon.CustomException;
import org.maiyon.model.APIEntity.CommonResponse;
import org.maiyon.model.APIEntity.Response;
import org.maiyon.model.APIEntity.WrapperStatus;
import org.maiyon.model.dto.request.ProductRequest;
import org.maiyon.model.dto.response.ProductReceive;
import org.maiyon.model.dto.response.ProductResponse;
import org.maiyon.model.entity.Product;
import org.maiyon.model.enums.ActiveStatus;
import org.maiyon.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/products")
public class AdminProductController {
    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<Product> products = productService.findAllToList();
        List<ProductReceive> productReceives = new ArrayList<>();
        products.forEach(product -> {
            ProductReceive productReceive = ProductReceive.builder()
                .id(product.getProductId())
                .sku(product.getSku())
                .productName(product.getProductName())
                .description(product.getDescription())
                .unitPrice(product.getUnitPrice())
                .stockQuantity(product.getStockQuantity())
                .image(product.getImage())
                .categoryId(product.getCategory().getCategoryId())
                .categoryName(product.getCategory().getCategoryName())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .status(product.getActiveStatus().equals(ActiveStatus.ACTIVE))
                .build();
            productReceives.add(productReceive);
        });
        return new ResponseEntity<>(new CommonResponse<>(
                HttpStatus.OK,
                productReceives
        ), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "5",name = "limit") int limit,
                                    @RequestParam(defaultValue = "0",name = "page") int page,
                                    @RequestParam(defaultValue = "productName",name = "sortBy") String sortBy,
                                    @RequestParam(defaultValue = "asc",name = "order") String orderBy){
        Pageable pageable;
        if(orderBy.equals("desc"))
            pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, sortBy));
        else pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, sortBy));
        Page<ProductResponse> products = productService.findActiveProductsToPage(pageable)
                .map(productService::entityMap);
        return new ResponseEntity<>(
            new Response<>(
                WrapperStatus.SUCCESS,
                HttpStatus.OK.name(),
                products.getContent()
            )
            , HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> findProductById(@PathVariable Long productId) throws CustomException{
        Optional<Product> product = productService.findById(productId);
        if(product.isPresent()){
            return new ResponseEntity<>(
                new Response<>(
                    WrapperStatus.SUCCESS,
                    HttpStatus.OK.name(),
                    productService.entityMap(product.get()))
                , HttpStatus.OK);
        }
        throw new CustomException("Product is not exists.");
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest productRequest) throws CustomException{
        logger.info("Create a new product.");
        Optional<Product> product = productService.save(productRequest);
        if (product.isPresent()){
            logger.info("Create category - success.");
            return new ResponseEntity<>(
                    new Response<>(
                            WrapperStatus.SUCCESS,
                            HttpStatus.CREATED.name(),
                            productService.entityMap(product.get()))
                    , HttpStatus.CREATED);
        }
        logger.error("Create product - failure.");
        throw new CustomException("Create product - failure.");
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> update(
            @PathVariable Long productId,
            @RequestBody @Valid ProductRequest productRequest)
    throws CustomException{
        logger.info("Update product.");
        productRequest.setCategoryId(productId);
        Optional<Product> product = productService.save(productRequest);
        if (product.isPresent()){
            logger.info("Update product - success.");
            return new ResponseEntity<>(
                    new Response<>(
                            WrapperStatus.SUCCESS,
                            HttpStatus.OK.name(),
                            productService.entityMap(product.get()))
                    , HttpStatus.OK);
        }
        logger.error("Update product - failure.");
        throw new CustomException("Update product - failure.");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteById(@PathVariable Long productId) throws CustomException{
        Boolean deleteProduct = productService.deleteById(productId);
        if(deleteProduct){
            logger.info("Delete product - success.");
            return new ResponseEntity<>(
                    new Response<>(
                            WrapperStatus.SUCCESS,
                            HttpStatus.OK.name(),
                            "Delete product by product id successfully.")
                    , HttpStatus.OK);
        }
        logger.error("Delete product - failure.");
        throw new CustomException("Delete product by product id failure.");
    }
}
