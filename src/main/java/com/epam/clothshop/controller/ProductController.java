package com.epam.clothshop.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.clothshop.dto.ProductRequest;
import com.epam.clothshop.dto.ProductResponse;
import com.epam.clothshop.entity.Category;
import com.epam.clothshop.entity.Product;
import com.epam.clothshop.service.CategoryService;
import com.epam.clothshop.service.ProductService;
import com.epam.clothshop.util.MapperUtils;

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
	public ResponseEntity<List<ProductResponse>> getAllProducts() {
		List<ProductResponse> productResponses = productService.getAllProducts().stream()
				.map(p -> MapperUtils.mapProductToProductResponse(p)).collect(Collectors.toList());
		return new ResponseEntity<>(productResponses, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable long id) {
		ProductResponse productResponse = MapperUtils.mapProductToProductResponse(productService.getProductById(id));
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Void> createProduct(@RequestBody ProductRequest productRequest) {
		Category category = categoryService.getCategoryById(productRequest.getCategoryId());
		productService.saveProduct(MapperUtils.mapProductRequestToProduct(productRequest, category));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateProduct(@PathVariable long id, @RequestBody ProductRequest productRequest) {
		Category category = categoryService.getCategoryById(productRequest.getCategoryId());
		Product product = MapperUtils.mapProductRequestToProduct(productRequest, category);
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
