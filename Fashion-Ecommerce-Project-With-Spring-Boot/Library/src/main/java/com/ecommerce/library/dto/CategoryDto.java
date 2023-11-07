package com.ecommerce.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class CategoryDto {
    private  Long categoryId;
    private String categoryName;
    private String image;
    private Long numberOfProduct;

    public CategoryDto(Long categoryId, String categoryName, String image, Long numberOfProduct) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.image = image;
        this.numberOfProduct = numberOfProduct;
    }
}
