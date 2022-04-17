package com.epam.clothshop.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Category {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;
private String name;

@OneToMany
private List products;
}
