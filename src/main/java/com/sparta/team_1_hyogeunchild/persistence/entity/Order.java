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

    @ManyToOne
    @JoinColumn(name = "User_Id")
    private User user;

    @JoinColumn
    private String storeName;

    @Column(nullable = false)
    private Long available;

    @Builder
    public Order(Long id, Long totalPrice, Long amount, Product product, User user, String storeName, Long available) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.amount = amount;
        this.product = product;
        this.user = user;
        this.storeName = storeName;
        this.available = available;
    }

    public void orderAvailable(Long available){
        this.available = available;
    }
}