package com.maiyon.service.impl;

import com.maiyon.model.dto.request.*;
import com.maiyon.model.dto.response.UserResponseForLogin;
import com.maiyon.model.dto.response.UserResponseToAdmin;
import com.maiyon.model.dto.response.UserResponseToUser;
import com.maiyon.model.entity.Role;
import com.maiyon.model.entity.User;
import com.maiyon.model.entity.enums.RoleName;
import com.maiyon.repository.RoleRepository;
import com.maiyon.repository.UserRepository;
import com.maiyon.security.jwt.JwtProvider;
import com.maiyon.security.user_principal.UserPrincipal;
import com.maiyon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtProvider jwtProvider;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public Boolean register(UserRegister userRegister) {
        if(userRepository.findByUsername(userRegister.getUsername()).isEmpty()){
            Role roleUser = roleRepository.findByRoleName(RoleName.ROLE_USER);
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(roleUser);
            User user = User.builder()
                    .username(userRegister.getUsername())
                    .password(new BCryptPasswordEncoder().encode(userRegister.getPassword()))
                    .fullName(userRegister.getFullName())
                    .email(userRegister.getEmail())
                    .phone(userRegister.getPhone())
                    .address(userRegister.getAddress())
                    .roles(userRoles)
                    .build();
            try{
                userRepository.save(user);
                return true;
            } catch(DataIntegrityViolationException e){
                logger.error("Constraint key error " + e);
            } catch(JpaSystemException e){
                logger.error("Jpa error " + e);
            } catch(Exception e){
                logger.error("Undetermined error " + e);
            }
        } else logger.error("User name is existed, can not sign up with this user name.");
        return false;
    }

    @Override
    public UserResponseForLogin login(UserLogin userLogin) {
        UserResponseForLogin userResponse = null;
        Authentication authentication;
        try {
            authentication = authenticationProvider.
                    authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(),userLogin.getPassword()));
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            String token = jwtProvider.generateToken(userPrincipal);
            userResponse = UserResponseForLogin.builder().
                    fullName(userPrincipal.getUser().getFullName())
                    .id(userPrincipal.getUser().getUserId()).token(token).
                    roles(userPrincipal.getAuthorities()
                            .stream().map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toSet())).
                    build();
        } catch (AuthenticationException e){
            logger.error("User or password is invalid");
            logger.error(e.getMessage());
        } catch (Exception e){
            logger.error("Undetermined error " + e);
        }
        return userResponse;
    }

    @Override
    public Page<UserResponseToAdmin> findAll(Pageable pageable) {
        List<User> users = userRepository.findAll();
        List<UserResponseToAdmin> userResponses = users.stream().map(this::builderUserResponseToAdmin).toList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), userResponses.size());
        return PageableExecutionUtils.getPage(userResponses.subList(start, end), pageable, userResponses::size);
    }

    @Override
    public Optional<UserResponseToAdmin> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::builderUserResponseToAdmin);
    }

    @Override
    public Optional<UserResponseToUser> getUserAccountDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            Optional<User> user = userRepository.findUserByUserId(userPrincipal.getUser().getUserId());
            return user.map(this::builderUserResponseToUser);
        }
        return Optional.empty();
    }

    @Override
    public UserResponseToUser updateUserAccountDetail(UserDetailRequest userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            User user = userPrincipal.getUser();
            user.setFullName(userRequest.getFullName());
            user.setEmail(userRequest.getEmail());
            user.setAvatar(userRequest.getAvatar());
            user.setPhone(userRequest.getPhone());
            user.setAddress(userRequest.getAddress());
            User updatedUser = userRepository.save(user);
            return builderUserResponseToUser(updatedUser);
        }
        return null;
    }

    @Override
    public UserResponseToUser updatePasswordAccount(UserChangePwdRequest userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            if (!passwordEncoder.matches(userRequest.getOldPassword(), userPrincipal.getPassword())) {
                logger.error("Old password not true.");
            } else if (userPrincipal.getPassword().equals(userRequest.getNewPassword())) {
                logger.error("New password must be different Old password.");
            } else if (!userRequest.getNewPassword().equals(userRequest.getNewPasswordConfirm())) {
                logger.error("New password confirm must like new password.");
            }
            User user = userPrincipal.getUser();
            user.setPassword(passwordEncoder.encode(userRequest.getNewPassword()));
            User updatedUser = userRepository.save(user);
            return builderUserResponseToUser(updatedUser);
        }
        return null;
    }

    @Override
    public Page<UserResponseToAdmin> searchByKeyword(Pageable pageable, String keyword) {
        List<User> users = userRepository.searchUsersByFullNameContainingIgnoreCase(keyword);
        List<UserResponseToAdmin> userResponses = users.stream().map(this::builderUserResponseToAdmin).toList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), userResponses.size());
        return PageableExecutionUtils.getPage(userResponses.subList(start, end), pageable, userResponses::size);
    }
    @Override
    public Optional<User> toggleUserStatus(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.toggleStatus();
            userOptional = Optional.of(userRepository.save(user));
            return userOptional;
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> addRoleForUserId(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            Optional<Role> role = roleRepository.findById(roleId);
            if (role.isPresent()){
                User modifyUser = user.get();
                Set<Role> userRoles = modifyUser.getRoles();
                userRoles.add(role.get());
                modifyUser.setRoles(userRoles);
                return Optional.of(userRepository.save(modifyUser));
            }
            logger.error("Can not found role to add for user.");
            return Optional.empty();
        }
        logger.error("Can not found user to add role.");
        return Optional.empty();
    }

    @Override
    public Optional<User> removeRoleForUserId(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            Optional<Role> role = roleRepository.findById(roleId);
            if (role.isPresent()){
                User modifyUser = user.get();
                Set<Role> userRoles = modifyUser.getRoles();
                userRoles.remove(role.get());
                modifyUser.setRoles(userRoles);
                return Optional.of(userRepository.save(modifyUser));
            }
            logger.error("Can not found role to remove for user.");
            return Optional.empty();
        }
        logger.error("Can not found user to remove role.");
        return Optional.empty();
    }

    public UserResponseToAdmin builderUserResponseToAdmin(User user){
        return UserResponseToAdmin.builder()
            .userId(user.getUserId())
            .username(user.getUsername())
            .password(user.getPassword())
            .fullName(user.getFullName())
            .userStatus(user.getUserStatus())
            .email(user.getEmail())
            .phone(user.getPhone())
            .address(user.getAddress())
            .createdAt(user.getCreateAt())
            .updatedAt(user.getUpdateAt())
            .roles(user.getRoles())
            .build();
    }
    public UserResponseToUser builderUserResponseToUser(User user){
        return UserResponseToUser.builder()
            .username(user.getUsername())
            .fullName(user.getFullName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .address(user.getAddress())
            .createdAt(user.getCreateAt())
            .updatedAt(user.getUpdateAt())
            .build();
    }
}
