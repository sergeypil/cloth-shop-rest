package com.epam.clothshop.controller;

import com.epam.clothshop.dto.ProductRequest;
import com.epam.clothshop.entity.Category;
import com.epam.clothshop.entity.Product;
import com.epam.clothshop.entity.Vendor;
import com.epam.clothshop.mapper.ProductMapper;
import com.epam.clothshop.service.CategoryService;
import com.epam.clothshop.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser
class ProductControllerTest {
    @MockBean
    ProductService productService;

    @MockBean
    CategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    MockMvc mockMvc;


    @Test
    void getAllProducts() throws Exception {
        Product product = new Product();
        product.setId(9);
        product.setName("mouse");
        Category category = new Category();
        category.setId(5);
        product.setCategory(category);
        Vendor vendor = new Vendor();
        vendor.setId(4);
        product.setVendor(vendor);
        List<Product> products = List.of(product);

        when(productService.getAllProducts()).thenReturn(products);

        this.mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("mouse")));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void getProductById() throws Exception {
        Product product = new Product();
        product.setId(9);
        product.setName("mouse");
        Category category = new Category();
        category.setId(5);
        product.setCategory(category);
        Vendor vendor = new Vendor();
        vendor.setId(4);
        product.setVendor(vendor);

        when(productService.getProductById(9)).thenReturn(product);

        this.mockMvc.perform(get("/products/9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("mouse")));

        verify(productService, times(1)).getProductById(9);
    }

    @Test
    void createProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("mouse");
        Category category = new Category();
        productRequest.setCategoryId(5);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(productRequest);

        when(categoryService.getCategoryById(5)).thenReturn(category);

        this.mockMvc.perform(post("/products")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(productService, times(1)).saveProduct(productMapper.mapProductRequestToProduct(productRequest, category));
    }

    @Test
    void updateProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("mouse");
        Category category = new Category();
        productRequest.setCategoryId(5);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(productRequest);

        when(categoryService.getCategoryById(5)).thenReturn(category);

        this.mockMvc.perform(put("/products/9")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(productService, times(1))
                .updateProduct(productMapper.mapProductRequestToProduct(productRequest, category), 9);
    }

    @Test
    void deleteProductById() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("mouse");
        Category category = new Category();
        productRequest.setCategoryId(5);

        Product product = productMapper.mapProductRequestToProduct(productRequest, category);
        when(productService.getProductById(9)).thenReturn(product);

        this.mockMvc.perform(delete("/products/9"))
                .andExpect(status().isOk());
        verify(productService, times(1)).getProductById(9);
        verify(productService, times(1)).deleteProduct(product);
    }

    @Test
    void getPhotoOfProduct() throws Exception {
        byte[] photoBytes = new byte[3];
        photoBytes[0] = 1;
        Product product = new Product();
        product.setId(9);
        product.setPhotoBytes(photoBytes);
        String photoBase64 = Base64.encodeBase64String(photoBytes);

        when(productService.getProductById(9)).thenReturn(product);

        this.mockMvc.perform(get("/products/9/photo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(photoBase64)));

        verify(productService, times(1)).getProductById(9);

    }

    @Test
    void updatePhotoOfProduct() throws Exception {
        String photoBase64 = "test";
        Product product = new Product();
        product.setId(9);
        byte[] photoBytes = Base64.decodeBase64(photoBase64);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(photoBase64);

        when(productService.getProductById(9)).thenReturn(product);

        this.mockMvc.perform(patch("/products/9/photo")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(productService, times(1))
                .updatePhotoOfProduct(product, 9, photoBytes);
    }
}