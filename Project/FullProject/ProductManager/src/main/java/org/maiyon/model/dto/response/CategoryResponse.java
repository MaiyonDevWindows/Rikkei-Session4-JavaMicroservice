package org.maiyon.model.dto.response;

import lombok.*;
import org.maiyon.model.entity.Category;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategoryResponse {
    private Long categoryId;
    private String categoryName;
    private String description;
    public static CategoryResponse entityMap(Category category){
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .build();
    }
}