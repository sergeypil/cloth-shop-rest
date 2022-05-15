package com.epam.clothshop.controller;

import com.epam.clothshop.dto.OrderRequest;
import com.epam.clothshop.dto.UserRequest;
import com.epam.clothshop.entity.Order;
import com.epam.clothshop.entity.User;
import com.epam.clothshop.mapper.OrderMapper;
import com.epam.clothshop.mapper.UserMapper;
import com.epam.clothshop.model.OrderStatus;
import com.epam.clothshop.service.ProductService;
import com.epam.clothshop.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser
class UserControllerTest {
    private static final int USER_ID = 4;
    private static final int ORDER_ID = 6;


    @MockBean
    UserService userService;

    @MockBean
    ProductService productService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllUsers() throws Exception {
        User user = createUserObject();
        List<User> users = List.of(user);

        Mockito.when(userService.getAllUsers()).thenReturn(users);

        this.mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(USER_ID)));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void createUser() throws Exception {
        UserRequest userRequest = new UserRequest();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userRequest);

        this.mockMvc.perform(post("/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(userService, times(1)).saveUser(userMapper.mapUserRequestToUser(userRequest));
    }

    @Test
    void getUserById() throws Exception {
        User user = createUserObject();

        Mockito.when(userService.getUserById(USER_ID)).thenReturn(user);

        this.mockMvc.perform(get("/users/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(USER_ID)));

        verify(userService, times(1)).getUserById(USER_ID);
    }

    @Test
    void updateUser() throws Exception {
        User user = new User();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(user);


        this.mockMvc.perform(put("/users/4")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).update(user, USER_ID);
    }

    @Test
    void deleteUserById() throws Exception {
        User user = createUserObject();

        when(userService.getUserById(USER_ID)).thenReturn(user);

        this.mockMvc.perform(delete("/users/4"))
                .andExpect(status().isOk());
        verify(userService, times(1)).getUserById(USER_ID);
        verify(userService, times(1)).deleteUser(user);
    }

    @Test
    void getOrdersByUser() throws Exception {
        User user = createUserObject();
        Order order = createOrderObject(user);
        List<Order> orders = List.of(order);
        user.setOrders(orders);

        Mockito.when(userService.getUserById(USER_ID)).thenReturn(user);

        this.mockMvc.perform(get("/users/4/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(ORDER_ID)));

        verify(userService, times(1)).getUserById(USER_ID);
    }

    @Test
    void createOrderForCustomer() throws Exception {
        User user = createUserObject();

        OrderRequest orderRequest = new OrderRequest();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(orderRequest);

        when(userService.getUserById(USER_ID)).thenReturn(user);

        this.mockMvc.perform(post("/users/4/orders")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(userService,times(1)).getUserById(USER_ID);
        verify(userService, times(1)).saveOrder(orderMapper.mapOrderRequestToOrder(orderRequest, user));
    }

    private User createUserObject() {
        var user = new User();
        user.setId(USER_ID);
        user.setOrders(new ArrayList<>());
        return user;
    }

    private Order createOrderObject(User user) {
        var order = new Order();
        order.setId(ORDER_ID);
        order.setUser(user);
        order.setStatus(OrderStatus.PLACED);
        order.setOrderItems(new ArrayList<>());
        return order;
    }
}