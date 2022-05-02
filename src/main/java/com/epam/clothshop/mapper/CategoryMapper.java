package com.epam.clothshop.mapper;

import com.epam.clothshop.dto.CategoryRequest;
import com.epam.clothshop.dto.CategoryResponse;
import com.epam.clothshop.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category mapCategoryRequestToCategory(CategoryRequest categoryRequest) {
        var category = new Category();
        category.setName(categoryRequest.getName());
        return category;
    }

    public CategoryResponse mapCategoryToCategoryResponse(Category category) {
        var categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        return categoryResponse;
    }
}
