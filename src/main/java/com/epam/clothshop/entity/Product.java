package com.epam.clothshop.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Product {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;
private String name;
private int price;
private int quantity;

@ManyToOne
@JoinColumn(name = "category_id")
private Category category;
}
