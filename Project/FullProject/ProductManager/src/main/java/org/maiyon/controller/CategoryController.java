package org.maiyon.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.maiyon.CustomException;
import org.maiyon.model.APIEntity.Response;
import org.maiyon.model.APIEntity.ResponseStatus;
import org.maiyon.model.dto.request.CategoryRequest;
import org.maiyon.model.dto.response.CategoryResponse;
import org.maiyon.model.entity.Category;
import org.maiyon.model.enums.ActiveStatus;
import org.maiyon.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping
    public ResponseEntity<?> getAllActiveCategoriesToPage(
            @RequestParam(defaultValue = "5",name = "limit") Integer limit,
            @RequestParam(defaultValue = "0",name = "page") Integer page,
            @RequestParam(defaultValue = "categoryName",name = "sort") String sortBy,
            @RequestParam(defaultValue = "asc", name = "direction") String orderBy){
        Pageable pageable = null;
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
                                ResponseStatus.SUCCESS,
                                HttpStatus.OK.name(),
                                categories.getContent())
                        , HttpStatus.OK);
            }
            logger.error("Get categories at page {} - failure", page);
            return new ResponseEntity<>(
                    new Response<>(
                            ResponseStatus.FAILURE,
                            HttpStatus.BAD_REQUEST.name(),
                            "Categories page is empty.")
                    , HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            logger.error("Get categories at page {} - failure", page);
            return new ResponseEntity<>(
                    new Response<>(
                            ResponseStatus.FAILURE,
                            HttpStatus.BAD_REQUEST.name(),
                            "Categories page out of range.")
                    , HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable("categoryId") Long categoryId){
        logger.info("Find category by categoryId.");
        Optional<Category> category = categoryService.findById(categoryId);
        if(category.isPresent()){
            return new ResponseEntity<>(
                    new Response<>(
                        ResponseStatus.SUCCESS,
                        HttpStatus.OK.name(),
                        categoryService.entityMap(category.get()))
            , HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new Response<>(
                        ResponseStatus.FAILURE,
                        HttpStatus.BAD_REQUEST.name(),
                        "Category is not exists.")
        , HttpStatus.BAD_REQUEST);
    }
    @PostMapping
    public ResponseEntity<?> createCategory(
            @RequestBody @Valid CategoryRequest categoryRequest)
                throws CustomException{
        logger.info("Create a new category.");
        Optional<Category> category = categoryService.save(categoryRequest);
        if (category.isPresent()){
            logger.info("Create category - success.");
            return new ResponseEntity<>(
                    new Response<>(
                            ResponseStatus.SUCCESS,
                            HttpStatus.CREATED.name(),
                            categoryService.entityMap(category.get()))
                    , HttpStatus.CREATED);
        }
        logger.error("Create category - failure.");
        return new ResponseEntity<>(
                new Response<>(
                        ResponseStatus.FAILURE,
                        HttpStatus.BAD_REQUEST.name(),
                        "Create category - failure.")
                , HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<?> overwriteCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody @Valid CategoryRequest categoryRequest)
                throws CustomException{
        logger.info("Update category.");
        categoryRequest.setCategoryId(categoryId);
        Optional<Category> category = categoryService.save(categoryRequest);
        if (category.isPresent()){
            logger.info("Update category - success.");
            return new ResponseEntity<>(
                    new Response<>(
                            ResponseStatus.SUCCESS,
                            HttpStatus.OK.name(),
                            categoryService.entityMap(category.get()))
                    , HttpStatus.OK);
        }
        logger.error("Update category - failure.");
        return new ResponseEntity<>(
                new Response<>(
                        ResponseStatus.FAILURE,
                        HttpStatus.BAD_REQUEST.name(),
                        "Update category - failure.")
                , HttpStatus.BAD_REQUEST);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable("id") Long categoryId,
            @RequestBody @Valid CategoryRequest categoryRequest) throws CustomException {
        logger.info("Update category by category Id.");
        Optional<Category> checkCategory = categoryService.findById(categoryId);
        if (checkCategory.isPresent()) {
            Category updateCategory = checkCategory.get();
            if (categoryRequest.getCategoryName() != null){
                updateCategory.setCategoryName(categoryRequest.getCategoryName());
                if(categoryService.findFirstByCategoryName(categoryRequest.getCategoryName()).isPresent())
                    throw new CustomException("Category name is already exists.");
            }
            if (categoryRequest.getDescription() != null)
                updateCategory.setDescription(categoryRequest.getDescription());
            if (categoryRequest.getActiveStatus() != null)
                updateCategory.setActiveStatus(
                        categoryRequest.getActiveStatus() ? ActiveStatus.ACTIVE : ActiveStatus.INACTIVE);
            Optional<Category> updatedCategory = categoryService.save(updateCategory);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        }logger.error("Update category - failure.");
        return new ResponseEntity<>(
                new Response<>(
                        ResponseStatus.FAILURE,
                        HttpStatus.BAD_REQUEST.name(),
                        "Update category - failure.")
                , HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId){
        Boolean deleteCategory = categoryService.deleteById(categoryId);
        if(deleteCategory){
            logger.info("Delete category - success.");
            return new ResponseEntity<>(
                    new Response<>(
                            ResponseStatus.SUCCESS,
                            HttpStatus.OK.name(),
                            "Delete category by category id successfully.")
                    , HttpStatus.OK);
        }
        logger.error("Delete category - failure.");
        return new ResponseEntity<>(
                new Response<>(
                        ResponseStatus.FAILURE,
                        HttpStatus.BAD_REQUEST.name(),
                        "Delete category by category id failure.")
                , HttpStatus.BAD_REQUEST);
    }
}