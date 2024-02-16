package org.maiyon.service;

import org.maiyon.model.dto.request.UserLogin;
import org.maiyon.model.dto.request.UserRegister;
import org.maiyon.model.dto.response.UserResponse;

public interface UserService {
    UserResponse handleLogin(UserLogin userLogin);
    String handleRegister(UserRegister userRegister);
}
