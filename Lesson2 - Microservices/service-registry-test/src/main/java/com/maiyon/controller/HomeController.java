package com.maiyon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {
    @GetMapping
    public ResponseEntity<?> home(){
        return new ResponseEntity<>("home", HttpStatus.OK);
    }
    @GetMapping("/students")
    public ResponseEntity<?> homeController(){
        List<String> strings=new ArrayList<>();
        strings.add("student 01");
        strings.add("student 02");
        return new ResponseEntity<>(strings, HttpStatus.OK);
    }
}
