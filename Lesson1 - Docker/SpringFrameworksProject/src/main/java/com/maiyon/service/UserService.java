package com.maiyon.service;

import com.maiyon.model.dto.request.UserChangePwdRequest;
import com.maiyon.model.dto.request.UserDetailRequest;
import com.maiyon.model.dto.request.UserLogin;
import com.maiyon.model.dto.request.UserRegister;
import com.maiyon.model.dto.response.UserResponseForLogin;
import com.maiyon.model.dto.response.UserResponseToAdmin;
import com.maiyon.model.dto.response.UserResponseToUser;
import com.maiyon.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    Boolean register(UserRegister user);
    UserResponseForLogin login(UserLogin userLogin);
    Page<UserResponseToAdmin> findAll(Pageable pageable);
    Page<UserResponseToAdmin> searchByKeyword(Pageable pageable, String keyword);
    Optional<UserResponseToAdmin> findById(Long id);
    Optional<UserResponseToUser> getUserAccountDetail();
    UserResponseToUser updateUserAccountDetail(UserDetailRequest userRequest);
    UserResponseToUser updatePasswordAccount(UserChangePwdRequest userRequest);
    Optional<User> toggleUserStatus(Long id);
    Optional<User> addRoleForUserId(Long userId, Long roleId);
    Optional<User> removeRoleForUserId(Long userId, Long roleId);
}