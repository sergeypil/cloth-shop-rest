package com.epam.clothshop.dto;

import com.epam.clothshop.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
