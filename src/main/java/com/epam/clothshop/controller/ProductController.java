package com.epam.clothshop.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.epam.clothshop.mapper.ProductMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.epam.clothshop.dto.ProductRequest;
import com.epam.clothshop.dto.ProductResponse;
import com.epam.clothshop.entity.Category;
import com.epam.clothshop.entity.Product;
import com.epam.clothshop.service.CategoryService;
import com.epam.clothshop.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	private ProductService productService;
	private CategoryService categoryService;
	private ProductMapper productMapper;

	@Autowired
	public ProductController(ProductService productService, CategoryService categoryService, ProductMapper productMapper) {
		this.productService = productService;
		this.categoryService = categoryService;
		this.productMapper = productMapper;
	}

	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAllProducts() {
		List<ProductResponse> productResponses = productService.getAllProducts().stream()
				.map(p -> productMapper.mapProductToProductResponse(p)).collect(Collectors.toList());
		return new ResponseEntity<>(productResponses, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable long id) {
		ProductResponse productResponse = productMapper.mapProductToProductResponse(productService.getProductById(id));
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Void> createProduct(@RequestBody ProductRequest productRequest) {
		Category category = categoryService.getCategoryById(productRequest.getCategoryId());
		productService.saveProduct(productMapper.mapProductRequestToProduct(productRequest, category));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateProduct(@PathVariable long id, @RequestBody ProductRequest productRequest) {
		Category category = categoryService.getCategoryById(productRequest.getCategoryId());
		Product product = productMapper.mapProductRequestToProduct(productRequest, category);
		productService.updateProduct(product, id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProductById(@PathVariable long id) {
		Product product = productService.getProductById(id);
		productService.deleteProduct(product);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/{id}/photo")
	public ResponseEntity<String> getPhotoOfProduct(@PathVariable long id) {
		Product product = productService.getProductById(id);
		byte[] photoBytes = product.getPhotoBytes();
		String photoBase64 = Base64.encodeBase64String(photoBytes);
		return new ResponseEntity<>(photoBase64, HttpStatus.OK);
	}

	@PatchMapping("/{id}/photo")
	public ResponseEntity<Void> updatePhotoOfProduct(@PathVariable long id, @RequestBody String photoBase64) {
		Product product = productService.getProductById(id);
		productService.updatePhotoOfProduct(product, id, Base64.decodeBase64(photoBase64));
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
