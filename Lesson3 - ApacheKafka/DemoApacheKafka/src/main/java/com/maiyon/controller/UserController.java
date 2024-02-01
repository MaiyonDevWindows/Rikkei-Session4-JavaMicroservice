package com.maiyon.controller;

import com.maiyon.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        kafkaTemplate.send("apache-demo", user);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
