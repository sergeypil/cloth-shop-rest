package com.epam.clothshop.repository;

import com.epam.clothshop.entity.Category;
import com.epam.clothshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
