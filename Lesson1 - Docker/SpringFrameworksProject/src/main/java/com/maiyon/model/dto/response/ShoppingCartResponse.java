package com.maiyon.model.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ShoppingCartResponse {
    String productName;
    Double unitPrice;
    Integer orderQuantity;
}
