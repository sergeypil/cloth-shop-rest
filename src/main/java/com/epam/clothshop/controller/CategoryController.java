package com.epam.clothshop.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.clothshop.dto.CategoryRequest;
import com.epam.clothshop.dto.CategoryResponse;
import com.epam.clothshop.dto.ProductResponse;
import com.epam.clothshop.service.CategoryService;
import com.epam.clothshop.util.MapperUtils;

@RestController
@RequestMapping("/categories")
@Component
public class CategoryController {
	CategoryService categoryService;
	
	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	public ResponseEntity<List<CategoryResponse>> getProducts() {
		List<CategoryResponse> categoryResponses = categoryService.getAllCategories().stream()
		.map(p -> MapperUtils.mapCategoryToCategoryResponse(p)).collect(Collectors.toList());
		return new ResponseEntity<>(categoryResponses, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponse> getProductById(@PathVariable long id) {
		CategoryResponse categoryResponse = MapperUtils.mapCategoryToCategoryResponse(categoryService.getCategoryById(id));
		return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Void> createProduct(@RequestBody CategoryRequest categoryRequest) {
		categoryService.saveCategory(MapperUtils.mapCategoryRequestToCategory(categoryRequest));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}/products")
	public ResponseEntity<List<ProductResponse>> getProductByCategoryId(@PathVariable long id) {
		List<ProductResponse> productResponses = categoryService.getProductsByCategoryId(id).stream()
		.map(p -> MapperUtils.mapProductToProductResponse(p)).collect(Collectors.toList());
		return new ResponseEntity<>(productResponses, HttpStatus.OK);
	}
}