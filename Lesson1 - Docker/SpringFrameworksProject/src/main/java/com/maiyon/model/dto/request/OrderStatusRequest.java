package com.maiyon.model.dto.request;

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
public class OrderStatusRequest {
    @NotNull(message = "Order Status must not be null.")
    @NotEmpty(message = "Order Status must not be empty.")
    String statusOrders;
}
