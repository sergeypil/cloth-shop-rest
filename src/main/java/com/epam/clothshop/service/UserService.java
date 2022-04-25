package com.epam.clothshop.service;

import com.epam.clothshop.entity.Order;
import com.epam.clothshop.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void saveUser(User user);

    User getUserById(long id);

    void update(User user, long id);

    void deleteUser(User user);

    void saveOrder(Order order);
}
