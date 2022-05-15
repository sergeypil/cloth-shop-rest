package com.epam.clothshop.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.epam.clothshop.mapper.CategoryMapper;
import com.epam.clothshop.mapper.ProductMapper;
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

@RestController
@RequestMapping("/categories")
@Component
public class CategoryController {
	private final CategoryService categoryService;
	private final CategoryMapper categoryMapper;
	private final ProductMapper productMapper;

	@Autowired
	public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper, ProductMapper productMapper) {
		this.categoryService = categoryService;
		this.categoryMapper = categoryMapper;
		this.productMapper = productMapper;
	}

	@GetMapping
	public ResponseEntity<List<CategoryResponse>> getAllCategories() {
		List<CategoryResponse> categoryResponses = categoryService.getAllCategories().stream()
		.map(p -> categoryMapper.mapCategoryToCategoryResponse(p)).collect(Collectors.toList());
		return new ResponseEntity<>(categoryResponses, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable long id) {
		CategoryResponse categoryResponse = categoryMapper.mapCategoryToCategoryResponse(categoryService.getCategoryById(id));
		return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Void> createCategory(@RequestBody CategoryRequest categoryRequest) {
		categoryService.saveCategory(categoryMapper.mapCategoryRequestToCategory(categoryRequest));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}/products")
	public ResponseEntity<List<ProductResponse>> getProductsByCategoryId(@PathVariable long id) {
		List<ProductResponse> productResponses = categoryService.getProductsByCategoryId(id).stream()
		.map(p -> productMapper.mapProductToProductResponse(p)).collect(Collectors.toList());
		return new ResponseEntity<>(productResponses, HttpStatus.OK);
	}
}