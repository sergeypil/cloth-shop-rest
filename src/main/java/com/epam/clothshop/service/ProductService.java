package com.epam.clothshop.service;

import java.util.List;

import com.epam.clothshop.dto.ProductRequest;
import com.epam.clothshop.dto.ProductResponce;
import com.epam.clothshop.entity.Product;

public interface ProductService {
	List<Product> getAllProducts();
	Product getProductById(long id);
	void saveProduct(Product product);
	void updateProduct(Product product, long id);
	void deleteProduct(Product product);
}
