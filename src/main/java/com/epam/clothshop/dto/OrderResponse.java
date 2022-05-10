package com.epam.clothshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private long id;
    private LocalDateTime shipDate;
    private LocalDateTime createdAt;
    private String status;
    private boolean isCompleted;
    private long userId;
    private List<OrderItemResponse> orderItemResponses;
}
