package com.epam.clothshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.clothshop.entity.Category;
import com.epam.clothshop.entity.Product;
import com.epam.clothshop.exception.ResourceNotFoundException;
import com.epam.clothshop.repository.CategoryRepository;
import com.epam.clothshop.repository.ProductRepository;
import com.epam.clothshop.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	
	@Autowired
	public CategoryServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Category getCategoryById(long id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found category with id " + id));
	}

	@Override
	public void saveCategory(Category category) {
		categoryRepository.save(category);
		
	}
	
	@Override
	public List<Product> getProductsByCategoryId(long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found category with id " + id));
		return productRepository.findByCategory(category);
	}




}
