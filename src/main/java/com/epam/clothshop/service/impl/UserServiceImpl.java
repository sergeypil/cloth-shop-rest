package com.epam.clothshop.service.impl;

import com.epam.clothshop.dto.UserResponse;
import com.epam.clothshop.entity.Order;
import com.epam.clothshop.entity.User;
import com.epam.clothshop.exception.ResourceNotFoundException;
import com.epam.clothshop.repository.OrderRepository;
import com.epam.clothshop.repository.UserRepository;
import com.epam.clothshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);

    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with id " + id));
    }

    @Override
    public void update(User user, long id) {
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
}
