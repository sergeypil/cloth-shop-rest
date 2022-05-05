package com.epam.clothshop.controller;

import com.epam.clothshop.dto.*;
import com.epam.clothshop.entity.Order;
import com.epam.clothshop.entity.User;
import com.epam.clothshop.mapper.OrderMapper;
import com.epam.clothshop.mapper.UserMapper;
import com.epam.clothshop.service.ProductService;
import com.epam.clothshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private ProductService productService;
    private UserMapper userMapper;
    private OrderMapper orderMapper;

    @Autowired
    public UserController(UserService userService, ProductService productService, UserMapper userMapper, OrderMapper orderMapper) {
        this.userService = userService;
        this.productService = productService;
        this.userMapper = userMapper;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponses = userService.getAllUsers().stream()
                .map(u -> userMapper.mapUserToUserResponce(u)).collect(Collectors.toList());
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserRequest userRequest) {
        userService.saveUser(userMapper.mapUserRequestToUser(userRequest));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(userMapper.mapUserToUserResponce(user), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable long id, @RequestBody UserRequest userRequest) {
        User user = userMapper.mapUserRequestToUser(userRequest);
        userService.update(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        userService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable long id) {
        User user = userService.getUserById(id);
        List<OrderResponse> orderResponses = user.getOrders().stream()
                .map(o -> orderMapper.mapOrderToOrderResponse(o)).collect(Collectors.toList());
        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<Void> createOrderForCustomer(@PathVariable long id, @RequestBody OrderRequest orderRequest) {
        User user = userService.getUserById(id);
        Order order = orderMapper.mapOrderRequestToOrder(orderRequest, user);
        userService.saveOrder(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
