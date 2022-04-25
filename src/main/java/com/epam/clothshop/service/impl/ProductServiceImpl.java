package com.epam.clothshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.clothshop.entity.Product;
import com.epam.clothshop.exception.ResourceNotFoundException;
import com.epam.clothshop.repository.CategoryRepository;
import com.epam.clothshop.repository.ProductRepository;
import com.epam.clothshop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	ProductRepository productRepository;
	CategoryRepository categoryRepository;
	
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();

	}

	@Override
	public Product getProductById(long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found product with id " + id));
	}

	@Override
	public void saveProduct(Product product) {
		productRepository.save(product);
	}

	@Override
	public void updateProduct(Product product, long id) {
		product.setId(id);
		productRepository.save(product);
	}

	@Override
	public void deleteProduct(Product product) {
		productRepository.delete(product);
	}
}
