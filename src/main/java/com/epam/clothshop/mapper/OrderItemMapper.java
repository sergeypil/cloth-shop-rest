package com.epam.clothshop.mapper;

import com.epam.clothshop.dto.OrderItemRequest;
import com.epam.clothshop.dto.OrderItemResponse;
import com.epam.clothshop.entity.Order;
import com.epam.clothshop.entity.OrderItem;
import com.epam.clothshop.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public OrderItem mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest, Product product, Order order) {
        var orderItem = new OrderItem();
        orderItem.setPrice(orderItemRequest.getPrice());
        orderItem.setQuantity(orderItemRequest.getQuantity());
        orderItem.setProduct(product);
        orderItem.setOrder(order);
        return orderItem;
    }

    public OrderItemResponse mapOrderItemToOrderItemResponse(OrderItem orderItem) {
        var orderItemResponse = new OrderItemResponse();
        orderItemResponse.setId(orderItem.getId());
        orderItemResponse.setPrice(orderItem.getPrice());
        orderItemResponse.setQuantity(orderItem.getQuantity());
        orderItemResponse.setProductId(orderItem.getProduct().getId());
        orderItemResponse.setOrderId(orderItem.getOrder().getId());
        return orderItemResponse;
    }
}
