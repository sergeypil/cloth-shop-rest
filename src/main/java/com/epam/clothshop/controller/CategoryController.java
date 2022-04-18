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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.clothshop.dto.CategoryRequest;
import com.epam.clothshop.dto.CategoryResponce;
import com.epam.clothshop.dto.ProductRequest;
import com.epam.clothshop.dto.ProductResponce;
import com.epam.clothshop.service.CategoryService;
import com.epam.clothshop.service.ProductService;
import com.epam.clothshop.util.utils;

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
	public ResponseEntity<List<CategoryResponce>> getProducts() {
		List<CategoryResponce> categoryResponces = categoryService.getAllCategories().stream()
		.map(p -> utils.mapCategoryToCategoryResponce(p)).collect(Collectors.toList());
		return new ResponseEntity<>(categoryResponces, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponce> getProductById(@PathVariable long id) {
		CategoryResponce categoryResponce = utils.mapCategoryToCategoryResponce(categoryService.getCategoryById(id));
		return new ResponseEntity<>(categoryResponce, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Void> createProduct(@RequestBody CategoryRequest categoryRequest) {
		categoryService.saveCategory(utils.mapCategoryRequestToCategory(categoryRequest));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}/products")
	public ResponseEntity<List<ProductResponce>> getProductByCategoryId(@PathVariable long id) {
		List<ProductResponce> productResponces = categoryService.getProductsByCategoryId(id).stream()
		.map(p -> utils.mapProductToProductResponce(p)).collect(Collectors.toList());
		return new ResponseEntity<>(productResponces, HttpStatus.OK);
	}
}