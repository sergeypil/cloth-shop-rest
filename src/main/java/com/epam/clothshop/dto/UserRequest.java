package com.epam.clothshop.dto;

import com.epam.clothshop.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String Phone;
    private String password;
}
