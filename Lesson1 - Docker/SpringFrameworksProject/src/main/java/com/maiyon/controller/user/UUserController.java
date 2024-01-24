package com.maiyon.controller.user;

import com.maiyon.model.dto.request.UserChangePwdRequest;
import com.maiyon.model.dto.request.UserDetailRequest;
import com.maiyon.model.dto.response.UserResponseToUser;
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
@RequestMapping("/v1/user/account")
public class UUserController {
    @Autowired
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping
    public ResponseEntity<?> getUserAccountDetail(){
        Optional<UserResponseToUser> userResponse = userService.getUserAccountDetail();
        if(userResponse.isPresent()){
            logger.info("User - Get user account info - successful.");
            return new ResponseEntity<>(userResponse.get(), HttpStatus.OK);
        }
        logger.error("User - Get user account info - failure.");
        return new ResponseEntity<>("Not authorize", HttpStatus.UNAUTHORIZED);
    }
    @PutMapping
    public ResponseEntity<?> updateUserAccountDetail(@RequestBody @Valid UserDetailRequest userRequest){
        Optional<UserResponseToUser> user = Optional.ofNullable(userService.updateUserAccountDetail(userRequest));
        if(user.isPresent()){
            logger.info("User - update data - success.");
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        logger.info("User - update data - failure.");
        return new ResponseEntity<>("Not Authorize", HttpStatus.UNAUTHORIZED);
    }
    @PutMapping("/change-password")
    public ResponseEntity<?> updatePasswordAccount(@RequestBody UserChangePwdRequest userRequest){
        Optional<UserResponseToUser> user = Optional.ofNullable(userService.updatePasswordAccount(userRequest));
        if(user.isPresent()){
            logger.info("User - change password - success.");
            return new ResponseEntity<>("Change password successfully.", HttpStatus.OK);
        }
        logger.info("User - change password - failure.");
        return new ResponseEntity<>("Not Authorize", HttpStatus.UNAUTHORIZED);
    }
}
