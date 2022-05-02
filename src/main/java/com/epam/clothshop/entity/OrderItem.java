package com.epam.clothshop.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int price;
    private int quantity;

    @OneToOne
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
