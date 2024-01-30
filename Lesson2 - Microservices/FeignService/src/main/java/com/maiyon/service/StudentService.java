package com.maiyon.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(url = "http://localhost:8882/students", name = "students")
public interface StudentService {
    @GetMapping
    List<String> getStudents();
}
