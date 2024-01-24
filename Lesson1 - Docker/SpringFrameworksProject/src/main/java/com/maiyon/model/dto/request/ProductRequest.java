package com.maiyon.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductRequest {
    private Long productId;
    @NotNull(message = "Product name must not be null.")
    @NotEmpty(message = "Product name must not be empty.")
    private String productName;
    private String description;
    private Double unitPrice;
    @Min(value = 0, message = "Stock quantity must be equals or greater than zero.")
    private Integer stockQuantity;
    private String imageUrl;
    private Long categoryId;
}