package com.epam.clothshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
	private long id;
	private String name;
	private int price;
	private int quantity;
	private CategoryResponse categoryResponse;
}
