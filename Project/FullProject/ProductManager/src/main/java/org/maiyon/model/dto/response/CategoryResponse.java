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
}