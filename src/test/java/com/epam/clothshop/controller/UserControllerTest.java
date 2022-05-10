package com.epam.clothshop.controller;

import com.epam.clothshop.dto.OrderRequest;
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
        User user = new User();
        user.setId(4);
        user.setOrders(new ArrayList<>());
        List<User> users = List.of(user);

        Mockito.when(userService.getAllUsers()).thenReturn(users);

        this.mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(4)));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void createUser() throws Exception {
        User user = new User();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(user);


        this.mockMvc.perform(post("/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    void getUserById() throws Exception {
        User user = new User();
        user.setId(4);
        user.setOrders(new ArrayList<>());

        Mockito.when(userService.getUserById(4)).thenReturn(user);

        this.mockMvc.perform(get("/users/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(4)));

        verify(userService, times(1)).getUserById(4);
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
        verify(userService, times(1)).update(user, 4);
    }

    @Test
    void deleteUserById() throws Exception {
        User user = new User();
        user.setId(4);

        when(userService.getUserById(4)).thenReturn(user);

        this.mockMvc.perform(delete("/users/4"))
                .andExpect(status().isOk());
        verify(userService, times(1)).getUserById(4);
        verify(userService, times(1)).deleteUser(user);
    }

    @Test
    void getOrdersByUser() throws Exception {
        User user = new User();
        user.setId(4);
        Order order = new Order();
        order.setId(6);
        order.setUser(user);
        order.setStatus(OrderStatus.PLACED);
        order.setOrderItems(new ArrayList<>());
        List<Order> orders = List.of(order);
        user.setOrders(orders);


        Mockito.when(userService.getUserById(4)).thenReturn(user);

        this.mockMvc.perform(get("/users/4/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(6)));

        verify(userService, times(1)).getUserById(4);
    }

    @Test
    void createOrderForCustomer() throws Exception {
        User user = new User();
        user.setId(4);


        OrderRequest orderRequest = new OrderRequest();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(orderRequest);

        when(userService.getUserById(4)).thenReturn(user);

        this.mockMvc.perform(post("/users/4/orders")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(userService,times(1)).getUserById(4);
        verify(userService, times(1)).saveOrder(orderMapper.mapOrderRequestToOrder(orderRequest, user));
    }
}