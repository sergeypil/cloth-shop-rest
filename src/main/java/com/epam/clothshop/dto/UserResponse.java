package com.epam.clothshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String Phone;
    private String password;
    private List<OrderResponse> orderResponses;
}
