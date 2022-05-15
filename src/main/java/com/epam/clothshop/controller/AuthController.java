package com.epam.clothshop.controller;

import com.epam.clothshop.dto.LoginRequest;
import com.epam.clothshop.dto.UserResponse;
import com.epam.clothshop.mapper.UserMapper;
import com.epam.clothshop.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller()
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;

    @Autowired
    public AuthController(AuthService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response,
                                              HttpServletRequest request) {
        UserResponse userResponse= userMapper.mapUserToUserResponce(
                authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword()));
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout () {
        authService.logout();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
