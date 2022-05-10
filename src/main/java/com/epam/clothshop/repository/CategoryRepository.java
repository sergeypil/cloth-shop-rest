package com.epam.clothshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.clothshop.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
