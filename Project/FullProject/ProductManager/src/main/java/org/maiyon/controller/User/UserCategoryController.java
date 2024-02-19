package org.maiyon.controller.User;

import lombok.RequiredArgsConstructor;
import org.maiyon.CustomException;
import org.maiyon.model.APIEntity.Response;
import org.maiyon.model.APIEntity.WrapperStatus;
import org.maiyon.model.dto.response.CategoryResponse;
import org.maiyon.service.CategoryService;
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
@RequestMapping("/v1/user/categories")
public class UserCategoryController {
    private final CategoryService categoryService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping
    public ResponseEntity<?> getAllActiveCategoriesToPage(
            @RequestParam(defaultValue = "5",name = "limit") Integer limit,
            @RequestParam(defaultValue = "0",name = "page") Integer page,
            @RequestParam(defaultValue = "categoryName",name = "sort") String sortBy,
            @RequestParam(defaultValue = "asc", name = "direction") String orderBy) throws CustomException {
        Pageable pageable;
        if(orderBy.equals("desc"))
            pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, sortBy));
        else pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, sortBy));
        try{
            Page<CategoryResponse> categories = categoryService.findActiveCategoriesToPage(pageable)
                    .map(categoryService::entityMap);
            if(!categories.isEmpty()){
                logger.info("Get categories at page {} - successful.", page);
                return new ResponseEntity<>(
                        new Response<>(
                                WrapperStatus.SUCCESS,
                                HttpStatus.OK.name(),
                                categories.getContent())
                        , HttpStatus.OK);
            }
            logger.error("Get categories at page {} - failure", page);
            throw new CustomException("Categories page is empty.");
        } catch (Exception e){
            logger.error("Get categories at page {} - failure", page);
            throw new CustomException("Categories page out of range.");
        }
    }
}