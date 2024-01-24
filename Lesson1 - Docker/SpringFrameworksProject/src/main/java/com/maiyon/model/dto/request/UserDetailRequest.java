package com.maiyon.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDetailRequest {
    @NotNull(message = "Full name must not be null.")
    @NotEmpty(message = "Full name must not be empty.")
    private String fullName;
    @jakarta.validation.constraints.Email(message = "Email is not valid, please try again.")
    private String Email;
    private String avatar;
    @Pattern(regexp = "^(0|\\+84)[3|5|7|8|9]\\d{8}$", message = "Phone must be VN format, head by (+84)")
    private String phone;
    @NotNull(message = "Address must not be null.")
    @NotEmpty(message = "Address must not be empty.")
    private String address;
}