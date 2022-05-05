package com.epam.clothshop.service;

import com.epam.clothshop.entity.User;

public interface AuthService {
    User loginUser(String username, String password);

    void logout();
}
