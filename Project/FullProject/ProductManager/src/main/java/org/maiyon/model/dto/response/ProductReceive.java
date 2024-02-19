package org.maiyon.model.dto.response;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductReceive {
    private Long id;
    private String sku;
    private String productName;
    private String description;
    private Double unitPrice;
    private Integer stockQuantity;
    private String image;
    private Long categoryId;
    private String categoryName;
    private Date createdAt;
    private Date updatedAt;
    private Boolean status;
}
