package com.epam.clothshop.dto;

import com.epam.clothshop.entity.Order;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class OrderItemRequest {
    private String name;
    private int price;
    private int quantity;
    private Order order;
}
