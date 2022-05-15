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
    private static final int PRODUCT_ID = 9;
    private static final int CATEGORY_ID = 5;
    private static final int VENDOR_ID = 4;
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
        Product product = createProductObject();
        Category category = createCategoryObject();
        product.setCategory(category);
        Vendor vendor = createVendorObject();
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
        Product product = createProductObject();
        Category category = createCategoryObject();
        product.setCategory(category);
        Vendor vendor = createVendorObject();
        product.setVendor(vendor);

        when(productService.getProductById(PRODUCT_ID)).thenReturn(product);

        this.mockMvc.perform(get("/products/9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("mouse")));

        verify(productService, times(1)).getProductById(PRODUCT_ID);
    }

    @Test
    void createProduct() throws Exception {
        ProductRequest productRequest = createProductRequestObject();
        Category category = new Category();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(productRequest);

        when(categoryService.getCategoryById(CATEGORY_ID)).thenReturn(category);

        this.mockMvc.perform(post("/products")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(productService, times(1)).saveProduct(productMapper.mapProductRequestToProduct(productRequest, category));
    }

    @Test
    void updateProduct() throws Exception {
        ProductRequest productRequest = createProductRequestObject();
        Category category = new Category();
        productRequest.setCategoryId(CATEGORY_ID);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(productRequest);

        when(categoryService.getCategoryById(CATEGORY_ID)).thenReturn(category);

        this.mockMvc.perform(put("/products/9")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(productService, times(1))
                .updateProduct(productMapper.mapProductRequestToProduct(productRequest, category), PRODUCT_ID);
    }

    @Test
    void deleteProductById() throws Exception {
        ProductRequest productRequest = createProductRequestObject();
        Category category = new Category();
        productRequest.setCategoryId(CATEGORY_ID);

        Product product = productMapper.mapProductRequestToProduct(productRequest, category);
        when(productService.getProductById(PRODUCT_ID)).thenReturn(product);

        this.mockMvc.perform(delete("/products/9"))
                .andExpect(status().isOk());
        verify(productService, times(1)).getProductById(PRODUCT_ID);
        verify(productService, times(1)).deleteProduct(product);
    }

    @Test
    void getPhotoOfProduct() throws Exception {
        Product product = createProductObject();
        String photoBase64 = Base64.encodeBase64String(product.getPhotoBytes());

        when(productService.getProductById(PRODUCT_ID)).thenReturn(product);

        this.mockMvc.perform(get("/products/9/photo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(photoBase64)));

        verify(productService, times(1)).getProductById(PRODUCT_ID);
    }

    @Test
    void updatePhotoOfProduct() throws Exception {
        String photoBase64 = "test";
        Product product = new Product();
        product.setId(PRODUCT_ID);
        byte[] photoBytes = Base64.decodeBase64(photoBase64);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(photoBase64);

        when(productService.getProductById(PRODUCT_ID)).thenReturn(product);

        this.mockMvc.perform(patch("/products/9/photo")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(productService, times(1))
                .updatePhotoOfProduct(product, PRODUCT_ID, photoBytes);
    }

    private Product createProductObject() {
        var product = new Product();
        product.setId(PRODUCT_ID);
        product.setName("mouse");
        byte[] photoBytes = new byte[3];
        product.setPhotoBytes(photoBytes);
        return product;
    }

    private Vendor createVendorObject() {
        var vendor = new Vendor();
        vendor.setId(VENDOR_ID);
        return vendor;
    }

    private Category createCategoryObject() {
        var category = new Category();
        category.setId(CATEGORY_ID);
        return category;
    }

    private ProductRequest createProductRequestObject() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("mouse");
        productRequest.setCategoryId(CATEGORY_ID);
        return productRequest;
    }
}