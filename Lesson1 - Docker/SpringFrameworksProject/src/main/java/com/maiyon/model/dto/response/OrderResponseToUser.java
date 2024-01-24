package com.maiyon.model.dto.response;

import com.maiyon.model.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderResponseToUser {
    private String serialNumber;
    private Double totalPrice;
    private OrderStatus orderStatus;
    private String note;
}
