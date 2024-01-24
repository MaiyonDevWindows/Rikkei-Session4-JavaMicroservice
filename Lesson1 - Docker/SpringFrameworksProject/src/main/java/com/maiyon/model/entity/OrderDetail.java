package com.maiyon.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "order_details")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId orderDetailId;
    @Column(name = "name")
    private String orderDetailName;
    private Double unitPrice;
    @Min(value = 1, message = "Order quantity must be greater than zero.")
    private int orderQuantity;
}