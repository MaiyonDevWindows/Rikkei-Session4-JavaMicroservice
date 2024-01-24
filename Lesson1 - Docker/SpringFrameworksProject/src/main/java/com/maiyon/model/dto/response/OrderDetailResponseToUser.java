package com.maiyon.model.dto.response;

import com.maiyon.model.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDetailResponseToUser {
    private String serialNumber;
    private Double totalPrice;
    private OrderStatus orderStatus;
    private String note;
    private String receiveName;
    private String receiveAddress;
    private String receivePhone;
    private Date createdAt;
    private Date receivedAt;
    private List<OrderDetailResponse> orderDetailResponses;
}
