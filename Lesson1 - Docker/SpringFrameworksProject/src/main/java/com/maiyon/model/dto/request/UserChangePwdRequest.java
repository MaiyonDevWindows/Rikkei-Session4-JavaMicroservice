package com.maiyon.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserChangePwdRequest {
    String oldPassword;
    String newPassword;
    String newPasswordConfirm;
}