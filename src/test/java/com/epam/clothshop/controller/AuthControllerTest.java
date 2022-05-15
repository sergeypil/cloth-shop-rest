package com.epam.clothshop.controller;

import com.epam.clothshop.dto.LoginRequest;
import com.epam.clothshop.entity.User;
import com.epam.clothshop.mapper.UserMapper;
import com.epam.clothshop.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser
class AuthControllerTest {

    @MockBean
    AuthService authService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void login() throws Exception {
        LoginRequest loginRequest = createLoginRequest();

        User user = createUser();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(loginRequest);

        when(authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword())).thenReturn(user);

        this.mockMvc.perform(post("/auth/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(authService, times(1)).loginUser(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @Test
    void logout() throws Exception {
        this.mockMvc.perform(post("/auth/logout"))
                .andExpect(status().isOk());
        verify(authService, times(1)).logout();
    }

    private User createUser() {
        var user = new User();
        user.setUsername("test");
        user.setOrders(new ArrayList<>());
        return user;
    }

    private LoginRequest createLoginRequest() {
        var loginRequest = new LoginRequest();
        loginRequest.setUsername("test");
        loginRequest.setPassword("123");
        return loginRequest;
    }
}