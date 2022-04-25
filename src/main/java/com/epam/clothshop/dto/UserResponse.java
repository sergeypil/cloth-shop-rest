package com.epam.clothshop.dto;

import com.epam.clothshop.entity.Order;

import javax.persistence.OneToMany;
import java.util.List;

public class UserResponse {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Order> orders;
}
