package com.epam.clothshop.service;

import java.util.List;

import com.epam.clothshop.entity.Category;
import com.epam.clothshop.entity.Product;

public interface CategoryService {
	List<Category> getAllCategories();
	Category getCategoryById(long id);
	void saveCategory(Category category);
	List<Product> getProductsByCategoryId(long id);
}
