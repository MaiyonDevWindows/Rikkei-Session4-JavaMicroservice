package com.maiyon.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ShoppingCartRequest {
    @NotNull(message = "ProductId must not be null")
    Long productId;
    @Min(value = 1,message = "Order quantity min value must be greater than zero.")
    Integer orderQuantity;
}
