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
    private static final int PRODUCT_ID = 5;
    private static final int ORDER_ID = 10;
    private static final int ORDER_ITEM_ID = 3;

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
        Order order = createOrderObject();
        List<Order> orders = List.of(order);

        Mockito.when(orderService.getAllOrders()).thenReturn(orders);

        this.mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(ORDER_ID)));

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void deleteOrderById() throws Exception {
        Order order = createOrderObject();

        when(orderService.getOrderById(ORDER_ID)).thenReturn(order);

        this.mockMvc.perform(delete("/orders/10"))
                .andExpect(status().isOk());
        verify(orderService, times(1)).getOrderById(ORDER_ID);
        verify(orderService, times(1)).deleteOrder(order);
    }

    @Test
    void cancelOrder() throws Exception {
        Order order = createOrderObject();

        when(orderService.getOrderById(ORDER_ID)).thenReturn(order);

        this.mockMvc.perform(post("/orders/10/cancel"))
                .andExpect(status().isOk());

        verify(orderService, times(1)).getOrderById(ORDER_ID);
        verify(orderService, times(1)).cancelOrder(order);
    }

    @Test
    void getOrderById() throws Exception {
        Order order = createOrderObject();

        Mockito.when(orderService.getOrderById(ORDER_ID)).thenReturn(order);

        this.mockMvc.perform(get("/orders/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(ORDER_ID)));

        verify(orderService, times(1)).getOrderById(ORDER_ID);
    }

    @Test
    void addItemToOrder() throws Exception {
        OrderItemRequest orderItemRequest = createOrderItemRequestObject();
        Order order = createOrderObject();

        Product product = createProductObject();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(orderItemRequest);

        Mockito.when(orderService.getOrderById(ORDER_ID)).thenReturn(order);
        Mockito.when(productService.getProductById(PRODUCT_ID)).thenReturn(product);

        this.mockMvc.perform(post("/orders/10/items")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(orderService, times(1)).getOrderById(ORDER_ID);
        verify(productService, times(1)).getProductById(PRODUCT_ID);
        verify(orderService, times(1))
                .addOrderItemToOrder(order, orderItemMapper.mapOrderItemRequestToOrderItem(orderItemRequest, product, order));
    }

    @Test
    void getItemOfOrder() throws Exception {
        Order order = createOrderObject();
        Product product = createProductObject();
        OrderItem orderItem = createOrderItemObject();
        orderItem.setOrder(order);
        orderItem.setProduct(product);

        Mockito.when(orderItemService.getOrderItemById(ORDER_ITEM_ID)).thenReturn(orderItem);

        this.mockMvc.perform(get("/orders/10/items/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(3)));

        verify(orderItemService, times(1)).getOrderItemById(3);
    }

    @Test
    void deleteItemOfOrder() throws Exception {
        OrderItem orderItem = createOrderItemObject();

        when(orderItemService.getOrderItemById(ORDER_ITEM_ID)).thenReturn(orderItem);

        this.mockMvc.perform(delete("/orders/10/items/3"))
                .andExpect(status().isOk());
        verify(orderItemService, times(1)).getOrderItemById(ORDER_ITEM_ID);
        verify(orderItemService, times(1)).deleteOrderItem(orderItem);
    }

    private Order createOrderObject() {
        var order = new Order();
        order.setId(ORDER_ID);
        order.setStatus(OrderStatus.PLACED);
        User user = new User();
        order.setUser(user);
        order.setOrderItems(new ArrayList<>());
        return order;
    }

    private Product createProductObject() {
        var product = new Product();
        product.setId(PRODUCT_ID);
        return product;
    }

    private OrderItemRequest createOrderItemRequestObject() {
        var orderItemRequest = new OrderItemRequest();
        orderItemRequest.setProductId(PRODUCT_ID);
        return orderItemRequest;
    }

    private OrderItem createOrderItemObject() {
        var orderItem = new OrderItem();
        orderItem.setId(ORDER_ITEM_ID);
        return orderItem;
    }
}