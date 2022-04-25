package com.epam.clothshop.dto;

import com.epam.clothshop.entity.OrderItem;
import com.epam.clothshop.entity.User;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

public class OrderRequest {
    private List<OrderItemRequest> orderItemRequests;
}
