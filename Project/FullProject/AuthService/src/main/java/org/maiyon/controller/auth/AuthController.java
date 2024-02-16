package org.maiyon.controller.auth;

import jakarta.validation.Valid;
import org.maiyon.model.dto.request.UserLogin;
import org.maiyon.model.dto.request.UserRegister;
import org.maiyon.model.dto.response.UserResponse;
import org.maiyon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/permit/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<UserResponse> handleLogin(@RequestBody @Valid UserLogin userLogin){
        return new ResponseEntity<>(userService.handleLogin(userLogin), HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<String> handleRegister(@RequestBody @Valid UserRegister userRegister){
        return new ResponseEntity<>(userService.handleRegister(userRegister),HttpStatus.CREATED);
    }
}
