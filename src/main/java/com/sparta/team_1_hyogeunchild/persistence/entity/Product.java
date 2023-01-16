package com.sparta.team_1_hyogeunchild.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;

@Entity(name="product")
@NoArgsConstructor
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="Product_ID")
    private Long id;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private Long amount;
    // 상품 양(남은 개수)
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private String storeName;
    private Long userId;

    public Product(Long id, String productName, Long amount, Long price, String storeName, Long userId) {
        this.id = id;
        this.productName = productName;
        this.amount = amount;
        this.price = price;
        this.storeName = storeName;
        this.userId = userId;
    }
}
