package com.epam.clothshop.mapper;

import com.epam.clothshop.dto.ProductRequest;
import com.epam.clothshop.dto.ProductResponse;
import com.epam.clothshop.entity.Category;
import com.epam.clothshop.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    CategoryMapper categoryMapper;

    @Autowired
    public ProductMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public Product mapProductRequestToProduct(ProductRequest productRequest, Category category) {
        var product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setCategory(category);
        return product;
    }

    public ProductResponse mapProductToProductResponse(Product product) {
        var productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setCategoryId(product.getCategory().getId());
        return productResponse;
    }
}
