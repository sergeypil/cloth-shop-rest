package com.epam.clothshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private long id;
    private int price;
    private int quantity;
    private long productId;
    private long orderId;
}
