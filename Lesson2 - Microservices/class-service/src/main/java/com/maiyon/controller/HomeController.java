package com.maiyon.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class HomeController {
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping
    public ResponseEntity<?> home(){
        Optional<String[]> stringList = Optional.ofNullable(restTemplate.getForObject("http://localhost:8882/students", String[].class));
        if(stringList.isPresent()){
            List<String> strings = Arrays.asList(stringList.get());
            return new ResponseEntity<>(strings, HttpStatus.OK);
        }
        return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }
}