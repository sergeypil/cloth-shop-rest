package com.epam.clothshop.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.epam.clothshop.entity.Category;
import com.epam.clothshop.entity.Product;

import lombok.Data;

@Data
//@NoArgsConstructor
@Entity
public class ProductDto {
private long id;
private String name;
private int price;
private int quantity;
private int categoryId;
}
