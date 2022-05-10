package com.epam.clothshop.controller;

import com.epam.clothshop.dto.OrderItemRequest;
import com.epam.clothshop.entity.*;
import com.epam.clothshop.mapper.OrderItemMapper;
import com.epam.clothshop.mapper.OrderMapper;
import com.epam.clothshop.model.OrderStatus;
import com.epam.clothshop.service.OrderItemService;
import com.epam.clothshop.service.OrderService;
import com.epam.clothshop.service.ProductService;
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
class OrderControllerTest {

    @MockBean
    OrderService orderService;

    @MockBean
    OrderItemService orderItemService;

    @MockBean
    ProductService productService;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllOrders() throws Exception {
        Order order = new Order();
        order.setId(10);
        order.setStatus(OrderStatus.PLACED);
        User user = new User();
        order.setUser(user);
        order.setOrderItems(new ArrayList<>());
        List<Order> orders = List.of(order);

        Mockito.when(orderService.getAllOrders()).thenReturn(orders);

        this.mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(10)));

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void deleteOrderById() throws Exception {
        Order order = new Order();
        order.setId(10);
        order.setStatus(OrderStatus.PLACED);
        User user = new User();
        order.setUser(user);
        order.setOrderItems(new ArrayList<>());

        when(orderService.getOrderById(10)).thenReturn(order);

        this.mockMvc.perform(delete("/orders/10"))
                .andExpect(status().isOk());
        verify(orderService, times(1)).getOrderById(10);
        verify(orderService, times(1)).deleteOrder(order);
    }

    @Test
    void cancelOrder() throws Exception {
        Order order = new Order();
        order.setId(10);
        order.setStatus(OrderStatus.PLACED);
        User user = new User();
        order.setUser(user);
        order.setOrderItems(new ArrayList<>());

        when(orderService.getOrderById(10)).thenReturn(order);

        this.mockMvc.perform(post("/orders/10/cancel"))
                .andExpect(status().isOk());

        verify(orderService, times(1)).getOrderById(10);
        verify(orderService, times(1)).cancelOrder(order);
    }

    @Test
    void purchaseOrder() {
    }

    @Test
    void getOrderById() throws Exception {
        Order order = new Order();
        order.setId(10);
        order.setStatus(OrderStatus.PLACED);
        User user = new User();
        order.setUser(user);
        order.setOrderItems(new ArrayList<>());

        Mockito.when(orderService.getOrderById(10)).thenReturn(order);

        this.mockMvc.perform(get("/orders/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(10)));

        verify(orderService, times(1)).getOrderById(10);
    }

    @Test
    void addItemToOrder() throws Exception {
        OrderItemRequest orderItemRequest = new OrderItemRequest();
        orderItemRequest.setProductId(5);

        Order order = new Order();
        order.setId(10);
        order.setStatus(OrderStatus.PLACED);
        User user = new User();
        order.setUser(user);
        order.setOrderItems(new ArrayList<>());

        Product product = new Product();
        product.setId(5);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(orderItemRequest);

        Mockito.when(orderService.getOrderById(10)).thenReturn(order);
        Mockito.when(productService.getProductById(5)).thenReturn(product);



        this.mockMvc.perform(post("/orders/10/items")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(orderService, times(1)).getOrderById(10);
        verify(productService, times(1)).getProductById(5);
        verify(orderService, times(1))
                .addOrderItemToOrder(order, orderItemMapper.mapOrderItemRequestToOrderItem(orderItemRequest, product, order));
    }

    @Test
    void getItemOfOrder() throws Exception {
        Order order = new Order();
        order.setId(10);
        order.setStatus(OrderStatus.PLACED);
        User user = new User();
        order.setUser(user);
        order.setOrderItems(new ArrayList<>());

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        Product product = new Product();
        orderItem.setProduct(product);
        orderItem.setId(3);

        Mockito.when(orderItemService.getOrderItemById(3)).thenReturn(orderItem);

        this.mockMvc.perform(get("/orders/10/items/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(3)));

        verify(orderItemService, times(1)).getOrderItemById(3);
    }

    @Test
    void deleteItemOfOrder() throws Exception {
        OrderItem orderItem = new OrderItem();

        orderItem.setId(3);

        when(orderItemService.getOrderItemById(3)).thenReturn(orderItem);

        this.mockMvc.perform(delete("/orders/10/items/3"))
                .andExpect(status().isOk());
        verify(orderItemService, times(1)).getOrderItemById(3);
        verify(orderItemService, times(1)).deleteOrderItem(orderItem);
    }
}