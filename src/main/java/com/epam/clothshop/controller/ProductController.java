package com.epam.clothshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.clothshop.dto.ProductDto;
import com.epam.clothshop.entity.Product;

@RestController
@RequestMapping("/p")
public class ProductController {

//	@GetMapping("/all")
//	public ProductDto getProducts() {
//		ProductDto productDto = new ProductDto();
//		productDto.setId(5);
//		return productDto;
//	}
	@ResponseBody
	@GetMapping("/test")
	public String getTsest() {

		return "dsdsd";
	}
	
	@RequestMapping("t")
	public String getTsFDest() {

		return "dsdsd";
	}
}
