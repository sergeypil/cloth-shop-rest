package com.epam.clothshop.controller;

import com.epam.clothshop.config.SecurityConfiguration;
import com.epam.clothshop.entity.Category;
import com.epam.clothshop.entity.Product;
import com.epam.clothshop.mapper.CategoryMapper;
import com.epam.clothshop.mapper.ProductMapper;
import com.epam.clothshop.repository.UserRepository;
import com.epam.clothshop.security.UserDetailsServiceImpl;
import com.epam.clothshop.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@WithMockUser
class CategoryControllerTest {

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
        Category category = new Category();
        category.setId(3);
        category.setName("books");
        category.setProducts(new ArrayList<>());
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
        Category category = new Category();
        category.setId(3);
        category.setName("books");
        category.setProducts(new ArrayList<>());

        Mockito.when(categoryService.getCategoryById(3)).thenReturn(category);

        this.mockMvc.perform(get("/categories/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("books")));

        verify(categoryService, times(1)).getCategoryById(3);
    }

    @Test
    void createCategory() throws Exception {
        Category category = new Category();
        category.setName("books");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(category);


        this.mockMvc.perform(post("/categories")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(categoryService, times(1)).saveCategory(category);
    }

    @Test
    void getProductsByCategoryId() throws Exception {
        Product product = new Product();
        product.setId(5);
        Category category = new Category();
        category.setId(3);
        product.setCategory(category);
        List<Product> products = List.of(product);

        Mockito.when(categoryService.getProductsByCategoryId(3)).thenReturn(products);

        this.mockMvc.perform(get("/categories/3/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(5)));

        verify(categoryService, times(1)).getProductsByCategoryId(3);
    }
}