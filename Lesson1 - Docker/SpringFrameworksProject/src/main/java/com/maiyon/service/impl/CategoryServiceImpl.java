package com.maiyon.service.impl;

import com.maiyon.model.dto.request.CategoryRequest;
import com.maiyon.model.dto.response.CategoryResponse;
import com.maiyon.model.entity.Category;
import com.maiyon.repository.CategoryRepository;
import com.maiyon.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    private final ModelMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    CategoryServiceImpl(){
        this.mapper = new ModelMapper();
    }
    private void configureModelMapper(){
        mapper.addMappings(new PropertyMap<Category, CategoryResponse>() {
            @Override
            protected void configure() {
            map().setId(source.getCategoryId());
            map().setCategoryName(source.getCategoryName());
            map().setDescription(source.getDescription());
            }
        });
        mapper.addMappings(new PropertyMap<CategoryRequest, Category>() {
            @Override
            protected void configure(){
                map().setCategoryId(source.getCategoryId());
                map().setCategoryName(source.getCategoryName());
                map().setDescription(source.getDescription());
            }
        });
    }
    @Override
    public Page<CategoryResponse> findAll(Pageable pageable){
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> categoryResponses = categories.stream().map(
                category -> mapper.map(category, CategoryResponse.class)
        ).toList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), categoryResponses.size());
        return PageableExecutionUtils.getPage(categoryResponses.subList(start, end), pageable, categoryResponses::size);
    }

    @Override
    public Optional<CategoryResponse> findById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        return Optional.ofNullable(mapper.map(categoryOptional, CategoryResponse.class));
    }

    @Override
    public Optional<CategoryResponse> save(CategoryRequest categoryRequest) {
        Category updateCategory = mapper.map(categoryRequest, Category.class);
        Category updatedCategory = categoryRepository.save(updateCategory);
        return Optional.ofNullable(mapper.map(updatedCategory, CategoryResponse.class));
    }

    @Override
    public Boolean delete(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isEmpty())  return false;
        categoryRepository.deleteById(id);
        return true;
    }
}
