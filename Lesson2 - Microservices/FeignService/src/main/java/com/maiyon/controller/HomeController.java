package com.maiyon.controller;

import com.maiyon.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {
    @Autowired
    private StudentService studentService;
    @GetMapping
    public ResponseEntity<?> home(){
        List<String> strings = studentService.getStudents();
        return new ResponseEntity<>(strings, HttpStatus.OK);
    }
}
