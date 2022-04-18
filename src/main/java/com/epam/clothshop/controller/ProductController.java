package com.epam.clothshop.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.clothshop.dto.ProductRequest;
import com.epam.clothshop.dto.ProductResponce;
import com.epam.clothshop.entity.Category;
import com.epam.clothshop.entity.Product;
import com.epam.clothshop.service.CategoryService;
import com.epam.clothshop.service.ProductService;
import com.epam.clothshop.util.utils;

@RestController
@RequestMapping("/products")
public class ProductController {
	ProductService productService;
	CategoryService categoryService;
	
	@Autowired
	public ProductController(ProductService productService, CategoryService categoryService) {
		this.productService = productService;
		this.categoryService = categoryService;
	}

	@GetMapping
	public ResponseEntity<List<ProductResponce>> getProducts() {
		List<ProductResponce> productResponces = productService.getAllProducts().stream()
				.map(p -> utils.mapProductToProductResponce(p)).collect(Collectors.toList());
		return new ResponseEntity<>(productResponces, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponce> getProductById(@PathVariable long id) {
		ProductResponce productResponce = utils.mapProductToProductResponce(productService.getProductById(id));
		return new ResponseEntity<>(productResponce, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Void> createProduct(@RequestBody ProductRequest productRequest) {
		Category category = categoryService.getCategoryById(productRequest.getCategoryId());
		productService.saveProduct(utils.mapProductRequestToProduct(productRequest, category));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateProduct(@PathVariable long id, @RequestBody ProductRequest productRequest) {
		Category category = categoryService.getCategoryById(productRequest.getCategoryId());
		Product product = utils.mapProductRequestToProduct(productRequest, category);
		productService.updateProduct(product, id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProductById(@PathVariable long id) {
		Product product = productService.getProductById(id);
		productService.deleteProduct(product);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
