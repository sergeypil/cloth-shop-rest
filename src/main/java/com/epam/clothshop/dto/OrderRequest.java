package com.epam.clothshop.dto;

import com.epam.clothshop.entity.OrderItem;
import com.epam.clothshop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private LocalDateTime shipDate;
    private List<OrderItemRequest> orderItemRequests;
}