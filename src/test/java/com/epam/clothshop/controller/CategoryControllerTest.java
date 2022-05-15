package com.epam.clothshop.controller;

import com.epam.clothshop.dto.CategoryRequest;
import com.epam.clothshop.entity.Category;
import com.epam.clothshop.entity.Product;
import com.epam.clothshop.mapper.CategoryMapper;
import com.epam.clothshop.mapper.ProductMapper;
import com.epam.clothshop.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser
class CategoryControllerTest {
    private static final int CATEGORY_ID = 3;
    private static final int PRODUCT_ID = 5;

    @MockBean
    CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllCategories() throws Exception {
        Category category = crateCategoryObject();
        List<Category> categories = List.of(category);

        Mockito.when(categoryService.getAllCategories()).thenReturn(categories);

        this.mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("books")));

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void getCategoryById() throws Exception {
        Category category = crateCategoryObject();

        Mockito.when(categoryService.getCategoryById(CATEGORY_ID)).thenReturn(category);

        this.mockMvc.perform(get("/categories/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("books")));

        verify(categoryService, times(1)).getCategoryById(CATEGORY_ID);
    }

    @Test
    void createCategory() throws Exception {
        CategoryRequest categoryRequest = crateCategoryRequestObject();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(categoryRequest);


        this.mockMvc.perform(post("/categories")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(categoryService, times(1))
                .saveCategory(categoryMapper.mapCategoryRequestToCategory(categoryRequest));
    }

    @Test
    void getProductsByCategoryId() throws Exception {
        Product product = createProductObject();
        List<Product> products = List.of(product);

        Mockito.when(categoryService.getProductsByCategoryId(CATEGORY_ID)).thenReturn(products);

        this.mockMvc.perform(get("/categories/3/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(PRODUCT_ID)));

        verify(categoryService, times(1)).getProductsByCategoryId(CATEGORY_ID);
    }

    private Category crateCategoryObject() {
        var category = new Category();
        category.setId(CATEGORY_ID);
        category.setName("books");
        category.setProducts(new ArrayList<>());
        return category;
    }

    private CategoryRequest crateCategoryRequestObject() {
        var categoryRequest = new CategoryRequest();
        categoryRequest.setName("books");
        return categoryRequest;
    }

    private Product createProductObject() {
        var product = new Product();
        product.setId(PRODUCT_ID);
        Category category = new Category();
        category.setId(CATEGORY_ID);
        product.setCategory(category);
        return product;
    }
}