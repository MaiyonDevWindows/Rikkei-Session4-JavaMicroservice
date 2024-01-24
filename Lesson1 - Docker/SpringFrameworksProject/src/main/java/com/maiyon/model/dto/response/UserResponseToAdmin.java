package com.maiyon.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maiyon.model.entity.Role;
import com.maiyon.model.entity.enums.ActiveStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponseToAdmin {
    private Long userId;
    private String username;
    private String password;
    private String fullName;
    private ActiveStatus userStatus;
    private String email;
    private String phone;
    private String address;
    private String avatar;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    private Date updatedAt;
    private Set<Role> roles;
}
