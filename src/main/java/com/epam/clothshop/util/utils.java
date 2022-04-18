package com.epam.clothshop.util;

import com.epam.clothshop.dto.CategoryRequest;
import com.epam.clothshop.dto.CategoryResponce;
import com.epam.clothshop.dto.ProductRequest;
import com.epam.clothshop.dto.ProductResponce;
import com.epam.clothshop.entity.Category;
import com.epam.clothshop.entity.Product;
import com.epam.clothshop.exception.ResourceNotFoundException;

public class utils {
	public static Product mapProductRequestToProduct(ProductRequest productRequest, Category category) {
		Product product = new Product();
		product.setName(productRequest.getName());
		product.setPrice(productRequest.getPrice());
		product.setQuantity(productRequest.getQuantity());
		product.setCategory(category);
		return product;
	}

	public static ProductResponce mapProductToProductResponce(Product product) {
		ProductResponce productResponce = new ProductResponce();
		productResponce.setId(product.getId());
		productResponce.setName(product.getName());
		productResponce.setPrice(product.getPrice());
		productResponce.setQuantity(product.getQuantity());
		productResponce.setCategoryId(product.getCategory().getId());
		return productResponce;
	}

	public static Category mapCategoryRequestToCategory(CategoryRequest categoryRequest) {
		Category category = new Category();
		category.setName(categoryRequest.getName());
		return category;
	}
	
	public static CategoryResponce mapCategoryToCategoryResponce(Category category) {
		CategoryResponce categoryResponce = new CategoryResponce();
		categoryResponce.setId(category.getId());
		categoryResponce.setName(category.getName());
		return categoryResponce;
	}
	

}
