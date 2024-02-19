package org.maiyon.controller.PermitAll;

import lombok.RequiredArgsConstructor;
import org.maiyon.model.APIEntity.Response;
import org.maiyon.model.APIEntity.WrapperStatus;
import org.maiyon.model.dto.response.ProductResponse;
import org.maiyon.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {
    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "5",name = "limit") int limit,
                                    @RequestParam(defaultValue = "0",name = "page") int page,
                                    @RequestParam(defaultValue = "productName",name = "sortBy") String sortBy,
                                    @RequestParam(defaultValue = "asc",name = "order") String orderBy){
        Pageable pageable;
        if(orderBy.equals("desc"))
            pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, sortBy));
        else pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, sortBy));
        Page<ProductResponse> products = productService.findActiveProductsToPage(pageable)
                .map(productService::entityMap);
        return new ResponseEntity<>(
                new Response<>(
                        WrapperStatus.SUCCESS,
                        HttpStatus.OK.name(),
                        products.getContent()
                )
                , HttpStatus.OK);
    }
}
