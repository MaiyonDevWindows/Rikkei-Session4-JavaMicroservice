package com.maiyon.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDetailResponse {
    private String name;
    private Double unitPrice;
    private Integer orderQuantity;
}
