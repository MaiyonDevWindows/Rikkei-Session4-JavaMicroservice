package com.maiyon.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddressResponse {
    private Long addressId;
    private String fullAddress;
    private String phone;
    private String receiveName;
}