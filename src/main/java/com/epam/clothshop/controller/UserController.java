package com.epam.clothshop.controller;

import com.epam.clothshop.dto.*;
import com.epam.clothshop.entity.Order;
import com.epam.clothshop.entity.User;
import com.epam.clothshop.service.UserService;
import com.epam.clothshop.util.MapperUtils;
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

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponses = userService.getAllUsers().stream()
                .map(u -> MapperUtils.mapUserToUserResponce(u)).collect(Collectors.toList());
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserRequest userRequest) {
        userService.saveUser(MapperUtils.mapUserRequestToUser(userRequest));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginUser() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(MapperUtils.mapUserToUserResponce(user), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable long id, @RequestBody UserRequest userRequest) {
        User user = MapperUtils.mapUserRequestToUser(userRequest);
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
                .map(o -> MapperUtils.mapOrderToOrderResponse(o)).collect(Collectors.toList());
        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<Void> createOrderForCustomer(@PathVariable long id, @RequestBody OrderRequest orderRequest) {
        User user = userService.getUserById(id);
        Order order = MapperUtils.mapOrderRequestToOrder(orderRequest, user);
        userService.saveOrder(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
