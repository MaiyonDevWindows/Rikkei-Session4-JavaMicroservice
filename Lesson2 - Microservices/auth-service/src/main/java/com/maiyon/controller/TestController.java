package com.maiyon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping
    public ResponseEntity<?> home(){
        return new ResponseEntity<>("Auth Service", HttpStatus.OK);
    }
}
