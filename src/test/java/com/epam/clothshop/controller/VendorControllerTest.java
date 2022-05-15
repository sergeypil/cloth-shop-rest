package com.epam.clothshop.controller;

import com.epam.clothshop.dto.VendorRequest;
import com.epam.clothshop.entity.Category;
import com.epam.clothshop.entity.Product;
import com.epam.clothshop.entity.Vendor;
import com.epam.clothshop.mapper.ProductMapper;
import com.epam.clothshop.mapper.VendorMapper;
import com.epam.clothshop.service.VendorService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser
class VendorControllerTest {
    private static final int VENDOR_ID = 3;
    private static final int PRODUCT_ID = 5;

    @MockBean
    VendorService vendorService;

    @Autowired
    private VendorMapper vendorMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllVendors() throws Exception {
        Vendor vendor = createVendorObject();
        List<Vendor> vendors = List.of(vendor);

        Mockito.when(vendorService.getAllVendors()).thenReturn(vendors);

        this.mockMvc.perform(get("/vendors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Piter")));

        verify(vendorService, times(1)).getAllVendors();
    }

    @Test
    void createVendor() throws Exception {
        VendorRequest vendorRequest = createVendorRequest();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(vendorRequest);


        this.mockMvc.perform(post("/vendors")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(vendorService, times(1))
                .saveVendor(vendorMapper.mapVendorRequestToVendor(vendorRequest));
    }

    @Test
    void getVendorById() throws Exception {
        Vendor vendor = createVendorObject();

        Mockito.when(vendorService.getVendorById(3)).thenReturn(vendor);

        this.mockMvc.perform(get("/vendors/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Piter")));

        verify(vendorService, times(1)).getVendorById(VENDOR_ID);
    }

    @Test
    void updateVendor() throws Exception {
        Vendor vendor = new Vendor();
        vendor.setName("Piter");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(vendor);


        this.mockMvc.perform(put("/vendors/3")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(vendorService, times(1)).update(vendor, VENDOR_ID);
    }

    @Test
    void deleteVendorById() throws Exception {
        Vendor vendor = new Vendor();
        vendor.setName("Piter");

        when(vendorService.getVendorById(VENDOR_ID)).thenReturn(vendor);

        this.mockMvc.perform(delete("/vendors/3"))
                .andExpect(status().isOk());
        verify(vendorService, times(1)).getVendorById(VENDOR_ID);
        verify(vendorService, times(1)).deleteVendor(vendor);
    }

    @Test
    void getProductsByVendor() throws Exception {
        Product product = createProductObject();
        Vendor vendor = createVendorObject();
        product.setVendor(vendor);
        List<Product> products = List.of(product);
        vendor.setProducts(products);

        Mockito.when(vendorService.getVendorById(3)).thenReturn(vendor);

        this.mockMvc.perform(get("/vendors/3/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(PRODUCT_ID)));

        verify(vendorService, times(1)).getVendorById(VENDOR_ID);
    }

    private Product createProductObject() {
        var product = new Product();
        product.setId(PRODUCT_ID);
        product.setCategory(new Category());
        return product;
    }

    private Vendor createVendorObject() {
        var vendor = new Vendor();
        vendor.setId(VENDOR_ID);
        vendor.setName("Piter");
        vendor.setProducts(new ArrayList<>());
        return vendor;
    }

    private VendorRequest createVendorRequest() {
        var vendorRequest = new VendorRequest();
        vendorRequest.setName("Piter");
        return vendorRequest;
    }
}