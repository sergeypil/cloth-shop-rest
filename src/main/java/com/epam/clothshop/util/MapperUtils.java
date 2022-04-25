package com.epam.clothshop.util;

import com.epam.clothshop.dto.*;
import com.epam.clothshop.entity.*;

public class MapperUtils {
	public static Product mapProductRequestToProduct(ProductRequest productRequest, Category category) {
		var product = new Product();
		product.setName(productRequest.getName());
		product.setPrice(productRequest.getPrice());
		product.setQuantity(productRequest.getQuantity());
		product.setCategory(category);
		return product;
	}

	public static ProductResponse mapProductToProductResponse(Product product) {
		var productResponse = new ProductResponse();
		productResponse.setId(product.getId());
		productResponse.setName(product.getName());
		productResponse.setPrice(product.getPrice());
		productResponse.setQuantity(product.getQuantity());
		productResponse.setCategoryResponse(mapCategoryToCategoryResponse(product.getCategory()));
		return productResponse;
	}

	public static Category mapCategoryRequestToCategory(CategoryRequest categoryRequest) {
		var category = new Category();
		category.setName(categoryRequest.getName());
		return category;
	}
	
	public static CategoryResponse mapCategoryToCategoryResponse(Category category) {
		var categoryResponse = new CategoryResponse();
		categoryResponse.setId(category.getId());
		categoryResponse.setName(category.getName());
		return categoryResponse;
	}


	public static OrderResponse mapOrderToOrderResponse(Order order) {
		var orderResponse = new OrderResponse();

		return orderResponse;
	}

	public static OrderItem mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest) {
		var orderItem = new OrderItem();
		return orderItem;
	}

	public static OrderItemResponse mapOrderItemToOrderItemResponse(OrderItem orderItem) {
		var orderItemResponse = new OrderItemResponse();
		return orderItemResponse;
	}

	public static UserResponse mapUserToUserResponce(User user) {
		var userResponse = new UserResponse();
		return userResponse;
	}

	public static User mapUserRequestToUser(UserRequest userRequest) {
		var user = new User();
		return user;
	}

	public static Order mapOrderRequestToOrder(OrderRequest orderRequest, User user) {
		var order = new Order();
		return order;
	}

	public static VendorResponse mapVendorToVendorResponse(Vendor vendor) {
		var vendorResponse = new VendorResponse();
		return vendorResponse;
	}

	public static Vendor mapVendorRequestToVendor(VendorRequest vendorRequest) {
		var vendor = new Vendor();
		return vendor;
	}
}
