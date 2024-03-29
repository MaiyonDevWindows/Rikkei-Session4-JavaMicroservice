package org.maiyon.model.dto.request;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.maiyon.model.entity.Category;
import org.maiyon.model.enums.ActiveStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategoryRequest {
    private Long categoryId;
    @NotNull(message = "Category name must not be null.")
    @NotEmpty(message = "Category name must not be empty.")
    private String categoryName;
    private String description;
    private Boolean activeStatus;
}
