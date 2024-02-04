package com.maiyon.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private String fullName;
    private String username;
    private String password;
    private String email;
}
