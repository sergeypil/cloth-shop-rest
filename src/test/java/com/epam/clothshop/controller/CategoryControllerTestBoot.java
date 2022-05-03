package com.epam.clothshop.controller;

import com.epam.clothshop.entity.Category;
import com.epam.clothshop.mapper.CategoryMapper;
import com.epam.clothshop.mapper.ProductMapper;
import com.epam.clothshop.service.CategoryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryController.class)
class CategoryControllerTestBoot {

    @MockBean
    CategoryService categoryService;

    @MockBean
    private CategoryMapper categoryMapper;

    @MockBean
    private ProductMapper productMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllCategories() throws Exception {
        Category category = new Category();
        category.setId(3);
        category.setName("books");
        category.setProducts(new ArrayList<>());
        List<Category> categories = List.of(category);

        Mockito.when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("books")));
    }

    @Test
    void getCategoryById() {
    }

    @Test
    void createCategory() {
    }

    @Test
    void getProductByCategoryId() {
    }
}