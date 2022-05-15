package com.epam.clothshop.mapper;

import com.epam.clothshop.dto.OrderItemResponse;
import com.epam.clothshop.dto.OrderRequest;
import com.epam.clothshop.dto.OrderResponse;
import com.epam.clothshop.entity.Order;
import com.epam.clothshop.entity.User;
import com.epam.clothshop.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    private final OrderItemMapper orderItemMapper;

    @Autowired
    public OrderMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    public OrderResponse mapOrderToOrderResponse(Order order) {
        var orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setShipDate(order.getShipDate());
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setStatus(order.getStatus().name());
        orderResponse.setCompleted(order.isCompleted());
        orderResponse.setUserId(order.getUser().getId());
        List<OrderItemResponse> orderItemResponses = order.getOrderItems().stream()
                .map(orderItemMapper::mapOrderItemToOrderItemResponse)
                .collect(Collectors.toList());
        orderResponse.setOrderItemResponses(orderItemResponses);
        return orderResponse;
    }

    public Order mapOrderRequestToOrder(OrderRequest orderRequest, User user) {
        var order = new Order();
        order.setShipDate(orderRequest.getShipDate());
        order.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        order.setStatus(OrderStatus.PLACED);
        order.setCompleted(false);
        order.setUser(user);
        return order;
    }
}
