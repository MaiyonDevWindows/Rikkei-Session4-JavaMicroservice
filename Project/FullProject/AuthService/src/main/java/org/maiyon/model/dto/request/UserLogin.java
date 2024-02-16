package org.maiyon.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserLogin {
    @NotNull(message = "User name must not be null.")
    @NotEmpty(message = "user name must not be empty.")
    @NotBlank(message = "User name must not be blank.")
    private String userName;
    @NotNull(message = "Password must not be null.")
    @NotEmpty(message = "Password must not be empty.")
    @NotBlank(message = "Password must not be blank.")
    private String password;
}
