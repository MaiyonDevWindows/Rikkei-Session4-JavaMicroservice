package com.maiyon.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRegister {
    @NotNull(message = "User name must not be null.")
    @NotEmpty(message = "User name must not be empty.")
    @Length(min = 6, max = 100,
            message = "User name must be at least 6 characters, and must not be more than 100 characters.")
    private String username;
    @NotNull(message = "Password must not be null.")
    @NotEmpty(message = "Password must not be empty.")
    private String password;
    @NotNull(message = "Full name must not be null.")
    @NotEmpty(message = "Full name must not be empty.")
    private String fullName;
    @Email(message = "Email is not valid, please try again.")
    private String email;
    @Pattern(regexp = "^(0|\\+84)[3|5|7|8|9]\\d{8}$", message = "Phone must be VN format, head by (+84)")
    private String phone;
    @NotNull(message = "Address must not be null.")
    @NotEmpty(message = "Address must not be empty.")
    private String address;
}
