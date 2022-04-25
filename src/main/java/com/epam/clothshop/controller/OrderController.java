package com.epam.clothshop.controller;

import com.epam.clothshop.dto.OrderItemRequest;
import com.epam.clothshop.dto.OrderItemResponse;
import com.epam.clothshop.dto.OrderResponse;
import com.epam.clothshop.entity.Order;
import com.epam.clothshop.entity.OrderItem;
import com.epam.clothshop.service.OrderItemService;
import com.epam.clothshop.service.OrderService;
import com.epam.clothshop.util.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@Component
public class OrderController {
    OrderService orderService;
    OrderItemService orderItemService;

    @Autowired
    public OrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orderResponses = orderService.getAllOrders().stream()
                .map(o -> MapperUtils.mapOrderToOrderResponse(o)).collect(Collectors.toList());
        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable long id) {
        Order order = orderService.getOrderById(id);
        orderService.deleteOrder(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable long id) {
        Order order = orderService.getOrderById(id);
        orderService.cancelOrder(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/purchase")
    public ResponseEntity<Void> purchaseOrder(@PathVariable long id) {
        Order order = orderService.getOrderById(id);
        orderService.purchaseOrder(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable long id) {
        OrderResponse orderResponse = MapperUtils.mapOrderToOrderResponse(orderService.getOrderById(id));
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<Void> addItemToOrder(@PathVariable long id, @RequestBody OrderItemRequest orderItemRequest) {
        Order order = orderService.getOrderById(id);
        OrderItem orderItem = MapperUtils.mapOrderItemRequestToOrderItem(orderItemRequest);
        orderService.addOrderItemToOrder(order, orderItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{oid}/items/{iid}")
    public ResponseEntity<OrderItemResponse> getItemOfOrder(@PathVariable long id) {
        OrderItemResponse orderItemResponse =
                MapperUtils.mapOrderItemToOrderItemResponse(orderItemService.getOrderItemById(id));
        return new ResponseEntity<>(orderItemResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{oid}/items/{iid}")
    public ResponseEntity<Void> deleteItemOfOrder(@PathVariable long id) {
        OrderItem orderItem = orderItemService.getOrderItemById(id);
        orderItemService.deleteOrderItem(orderItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
