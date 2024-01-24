package com.maiyon.model.dto.response;

import com.maiyon.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductResponse {
    private Long id;
    private String productName;
    private String description;
    private Double unitPrice;
    private Integer stockQuantity;
    private String imageUrl;
    private Category category;
}
