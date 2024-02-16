package org.maiyon.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponse {
    private Long userId;
    private String accessToken;
    private final String type = "Bearer";
    private String fullName;
    private String userName;
    private Set<String> roles;
    private String email;
    private Boolean status;
}
