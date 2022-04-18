package com.epam.clothshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.clothshop.entity.Category;
import com.epam.clothshop.entity.Product;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
