package com.epam.clothshop.service;

import com.epam.clothshop.entity.Order;
import com.epam.clothshop.entity.OrderItem;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    Order getOrderById(long id);

    void deleteOrder(Order order);

    void cancelOrder(Order order);

    void addOrderItemToOrder(Order order, OrderItem orderItem);
}
