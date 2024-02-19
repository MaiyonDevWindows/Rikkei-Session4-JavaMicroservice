package com.ra.model.dto.receive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryReceive{
    private Long id;
    private String description;
    private String categoryName;
    private Boolean status;
    private List<ProductLite> product;
}
