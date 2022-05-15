package com.epam.clothshop.mapper;

import com.epam.clothshop.dto.OrderResponse;
import com.epam.clothshop.dto.UserRequest;
import com.epam.clothshop.dto.UserResponse;
import com.epam.clothshop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    private final OrderMapper orderMapper;

    @Autowired
    public UserMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public UserResponse mapUserToUserResponce(User user) {
        var userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        List<OrderResponse> orderResponses = user.getOrders().stream()
                        .map(orderMapper::mapOrderToOrderResponse)
                        .collect(Collectors.toList());
        userResponse.setOrderResponses(orderResponses);
        return userResponse;
    }

    public User mapUserRequestToUser(UserRequest userRequest) {
        var user = new User();
        user.setUsername(userRequest.getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setPassword(userRequest.getPassword());
        return user;
    }
}
