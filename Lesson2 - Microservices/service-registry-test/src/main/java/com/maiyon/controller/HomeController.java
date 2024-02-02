package com.maiyon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class HomeController {
    @GetMapping
    public ResponseEntity<?> getAllStudents(){
        List<String> strings=new ArrayList<>();
        strings.add("student 01");
        strings.add("student 02");
        return new ResponseEntity<>(strings, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") String studentId){
        return new ResponseEntity<>(studentId, HttpStatus.OK);
    }
}
