package com.maiyon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class StudentService {
    public static void main(String[] args) {
        SpringApplication.run(StudentService.class, args);
    }
}
