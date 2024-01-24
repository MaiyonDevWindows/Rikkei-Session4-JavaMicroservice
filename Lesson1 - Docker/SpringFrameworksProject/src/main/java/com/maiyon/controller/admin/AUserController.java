package com.maiyon.controller.admin;

import com.maiyon.model.dto.response.UserResponseToAdmin;
import com.maiyon.model.entity.User;
import com.maiyon.model.entity.enums.ActiveStatus;
import com.maiyon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin/users")
public class AUserController {
    @Autowired
    private UserService userService;
    private final Logger logger =
            LoggerFactory.getLogger(AUserController.class);
    @GetMapping
    public ResponseEntity<?> getAllUsers(
        @RequestParam(defaultValue = "5", name = "limit") Integer limit,
        @RequestParam(defaultValue = "0", name = "page") Integer page,
        @RequestParam(defaultValue = "userName", name = "sort") String sortBy,
        @RequestParam(defaultValue = "asc", name = "direction") String orderBy){
        Pageable pageable = PageRequest.of(page, limit, Sort.by(orderBy, sortBy));
        try{
            Page<UserResponseToAdmin> userResponses = userService.findAll(pageable);
            if(!userResponses.isEmpty()){
                logger.info("Admin - Get users at page {} - successful.", page);
                return new ResponseEntity<>(userResponses.getContent(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Users page is empty.", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            logger.error("Admin - Get users at page {} - failure.", page);
            return new ResponseEntity<>("Users page out of range.", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId){
        Optional<UserResponseToAdmin> userResponse = userService.findById(userId);
        if(userResponse.isPresent()){
            logger.info("Admin - Get user by id: {}  - successful.", userId);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        }
        logger.error("Admin - Get user by id: {} - failure.", userId);
        return new ResponseEntity<>("User is not exists.", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchUserByFullName(
            @PathVariable("keyword") String keyword,
            @RequestParam(defaultValue = "5", name = "limit") Integer limit,
            @RequestParam(defaultValue = "0", name = "page") Integer page,
            @RequestParam(defaultValue = "userName", name = "sort") String sortBy,
            @RequestParam(defaultValue = "asc", name = "direction") String orderBy){
        Pageable pageable = PageRequest.of(page, limit, Sort.by(orderBy, sortBy));
        try{
            Page<UserResponseToAdmin> userResponses = userService.searchByKeyword(pageable, keyword);
            if(!userResponses.isEmpty()){
                logger.info("Admin - Find users with keyword {} at page {} - successful.", keyword, page);
                return new ResponseEntity<>(userResponses.getContent(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Users page is empty.", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            logger.error("Admin - Get users with keyword {} at page {} - failure.", keyword, page);
            return new ResponseEntity<>("Users page out of range.", HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{userId}")
    public ResponseEntity<?> toggleUserStatus(@PathVariable("userId") Long userId){
        Optional<User> user = userService.toggleUserStatus(userId);
        if (user.isPresent()){
            if(user.get().getUserStatus() == ActiveStatus.ACTIVE){
                logger.info("Admin - Unblock user - success.");
                return new ResponseEntity<>("Unblock user - success.", HttpStatus.OK);
            }else{
                logger.info("Admin - Block user - success.");
                return new ResponseEntity<>("Block user - success.", HttpStatus.OK);
            }
        }
        logger.error("Admin - change user status - failure.");
        return new ResponseEntity<>("Change user status failure.", HttpStatus.NOT_FOUND);
    }
    @PostMapping("/{userId}/role/{roleId}")
    public ResponseEntity<?> addRoleForUserId(
            @PathVariable("userId") Long userId,
            @PathVariable("roleId") Long roleId){
        Optional<User> user = userService.addRoleForUserId(userId, roleId);
        if(user.isPresent()){
            logger.info("Admin - Add permission - successful.");
            return new ResponseEntity<>("Add permission success.", HttpStatus.OK);
        }
        logger.error("Admin - Add permission - failure.");
        return new ResponseEntity<>("Add permission failure.", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{userId}/role/{roleId}")
    public ResponseEntity<?> deleteRoleForUserId(
            @PathVariable("userId") Long userId,
            @PathVariable("roleId") Long roleId){
        Optional<User> user = userService.removeRoleForUserId(userId, roleId);
        if(user.isPresent()){
            logger.info("Admin - remove permission - successful.");
            return new ResponseEntity<>("remove permission success.", HttpStatus.OK);
        }
        logger.error("Admin - remove permission - failure.");
        return new ResponseEntity<>("remove permission failure.", HttpStatus.NOT_FOUND);
    }
}
