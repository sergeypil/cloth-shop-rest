package com.epam.clothshop.service.impl;

import com.epam.clothshop.entity.Order;
import com.epam.clothshop.entity.OrderItem;
import com.epam.clothshop.exception.ResourceNotFoundException;
import com.epam.clothshop.model.OrderStatus;
import com.epam.clothshop.repository.OrderItemRepository;
import com.epam.clothshop.repository.OrderRepository;
import com.epam.clothshop.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;


    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found order with id " + id));
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public void cancelOrder(Order order) {
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

    }

    @Override
    public void purchaseOrder(Order order) {

    }

    @Override
    public void addOrderItemToOrder(Order order, OrderItem orderItem) {
        order.getOrderItems().add(orderItem);
        orderRepository.save(order);
    }
}
