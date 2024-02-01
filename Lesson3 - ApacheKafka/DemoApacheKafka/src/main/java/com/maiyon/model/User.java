package com.maiyon.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    private String fullName;
    private String username;
    private String password;
    private String email;
}
