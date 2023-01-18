package com.sparta.team_1_hyogeunchild.persistence.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name ="orders")
@NoArgsConstructor
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_ID")
    private Long id;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "Product_ID")
    private Product product;
    @Column(nullable = false)
    private String username;

    @Builder
    public Order(Long id, Long totalPrice, Long amount, Product product, String username) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.amount = amount;
        this.product = product;
        this.username = username;
    }
}