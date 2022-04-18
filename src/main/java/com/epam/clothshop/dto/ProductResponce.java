package com.epam.clothshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponce {
	private long id;
	private String name;
	private int price;
	private int quantity;
	private long categoryId;
}
