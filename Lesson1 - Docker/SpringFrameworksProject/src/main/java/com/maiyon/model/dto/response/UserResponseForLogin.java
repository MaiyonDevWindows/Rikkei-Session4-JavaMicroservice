package com.maiyon.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponseForLogin {
    private Long id;
    private String fullName;
    private String token;
    private final String type = "Bearer Token";
    private Set<String> roles;
}
