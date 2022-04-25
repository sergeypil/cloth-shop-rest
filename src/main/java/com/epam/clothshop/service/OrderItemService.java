package com.epam.clothshop.service;

import com.epam.clothshop.entity.OrderItem;

public interface OrderItemService {
    OrderItem getOrderItemById(long id);

    void deleteOrderItem(OrderItem orderItem);
}
