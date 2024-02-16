package org.maiyon.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserRegister {
    @NotEmpty(message = "Full name must not be empty.")
    private String fullName;
    @NotEmpty(message = "User name must not be empty.")
    private String userName;
    @NotEmpty(message = "Password must not be empty.")
    @Length(min=6, max=30, message = "Password must be between 6 and 30")
    private String password;
}
