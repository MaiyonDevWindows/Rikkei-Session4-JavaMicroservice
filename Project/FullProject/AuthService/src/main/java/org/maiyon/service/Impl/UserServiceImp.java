package org.maiyon.service.Impl;

import org.maiyon.model.dto.request.UserLogin;
import org.maiyon.model.dto.request.UserRegister;
import org.maiyon.model.dto.response.UserResponse;
import org.maiyon.model.entity.Role;
import org.maiyon.model.entity.User;
import org.maiyon.model.enums.RoleName;
import org.maiyon.repository.UserRepository;
import org.maiyon.security.jwt.JwtProvider;
import org.maiyon.security.userPrinciple.UserPrinciple;
import org.maiyon.service.RoleService;
import org.maiyon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public UserResponse handleLogin(UserLogin userLogin) {
        Authentication authentication;
        try{
            authentication = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(userLogin.getUserName(),userLogin.getPassword())
            );
        }catch (AuthenticationException authenticationException){
            throw new RuntimeException("User name or password is wrong.");
        }
        UserPrinciple userPrinciple= (UserPrinciple) authentication.getPrincipal();
        String token=jwtProvider.generateToken(userPrinciple);
        return UserResponse.builder()
            .fullName(userPrinciple.getUser().getFullName())
            .userName(userPrinciple.getUser().getUserName())
            .status(userPrinciple.getUser().getStatus())
            .userId(userPrinciple.getUser().getUserId()).accessToken(token)
            .roles(userPrinciple.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
            .email(userPrinciple.getUser().getEmail())
            .build();
    }
    @Override
    public String handleRegister(UserRegister userRegister) {
        if(userRepository.existsByUserName(userRegister.getUserName()))
            throw new RuntimeException("User name is exists.");
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        User user = User.builder()
                    .fullName(userRegister.getFullName()).userName(userRegister.getUserName())
                    .password(passwordEncoder.encode(userRegister.getPassword()))
                    .status(true)
                    .roles(roles)
                .build();
        userRepository.save(user);
        return "Success";
    }





}
