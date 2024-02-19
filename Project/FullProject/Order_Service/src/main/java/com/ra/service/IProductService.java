package com.ra.service;

import com.ra.model.dto.receive.CommonReceive;
import com.ra.model.dto.receive.ProductReceive;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url = "http://localhost:8886/v1/admin/products",name = "Product")
public interface IProductService {
    @GetMapping("/getAll")
    CommonReceive<List<ProductReceive>> getAll();
}
