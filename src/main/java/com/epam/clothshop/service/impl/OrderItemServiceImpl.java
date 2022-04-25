package com.epam.clothshop.service.impl;

import com.epam.clothshop.entity.OrderItem;
import com.epam.clothshop.exception.ResourceNotFoundException;
import com.epam.clothshop.repository.OrderItemRepository;
import com.epam.clothshop.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderItemServiceImpl implements OrderItemService {
    OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem getOrderItemById(long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found orderItem with id " + id));
    }

    @Override
    public void deleteOrderItem(OrderItem orderItem) {
        orderItemRepository.delete(orderItem);
    }
}
