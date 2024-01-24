package com.maiyon.controller.auth;

import com.maiyon.model.dto.request.UserLogin;
import com.maiyon.model.dto.request.UserRegister;
import com.maiyon.model.dto.response.UserResponseForLogin;
import com.maiyon.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserRegister userRegister){
        if(userService.register(userRegister)){
            logger.info("Any - User registered with user name: {} - successful", userRegister.getUsername());
            return new ResponseEntity<>("Register successful!",HttpStatus.CREATED);
        }
        logger.error("Any - User registered with user name: {} - failure.", userRegister.getUsername());
        return new ResponseEntity<>("Register failure.",HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin){
        Optional<UserResponseForLogin> userOptional = Optional.ofNullable(userService.login(userLogin));
        if(userOptional.isPresent()){
            logger.info("User {} logged in.", userLogin.getUsername());
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        }
        logger.error("Username or password is incorrect.");
        return new ResponseEntity<>("Username or password incorrect!", HttpStatus.UNAUTHORIZED);
    }
}
