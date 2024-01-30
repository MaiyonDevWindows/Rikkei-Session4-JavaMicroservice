package com.maiyon.model.entity;

import com.maiyon.model.enums.ActiveStatus;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private Integer quantityStock;
    private Double unitPrice;
    private String description;
    @Enumerated
    @Column(name = "product_status", columnDefinition = "BIT(1) DEFAULT(1)")
    private ActiveStatus product_status;
}
