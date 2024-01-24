package com.maiyon.controller.admin;

import com.maiyon.model.entity.Role;
import com.maiyon.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/roles")
public class ARoleController {
    @Autowired
    private RoleService roleService;
    private final Logger logger = LoggerFactory.getLogger(ARoleController.class);
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Role> roles = roleService.findAll();
        if(!roles.isEmpty()){
            logger.info("Admin - Get all roles - successful.");
            return new ResponseEntity<>(roles, HttpStatus.OK);
        }
        logger.error("Admin - Get all roles - failure.");
        return new ResponseEntity<>("Roles list is empty.", HttpStatus.NOT_FOUND);
    }
}
