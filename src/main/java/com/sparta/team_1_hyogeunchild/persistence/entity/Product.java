package com.sparta.team_1_hyogeunchild.persistence.entity;

import com.sparta.team_1_hyogeunchild.business.dto.ProductRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity(name="product")
@NoArgsConstructor
@Getter
public class Product extends Timestaped {
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
    private String username;

    @Column(nullable = false)
    private String storeName;

    @Builder
    public Product(Long id, String productName, Long amount, Long price, String storeName, String username) {
        this.id = id;
        this.productName = productName;
        this.amount = amount;
        this.price = price;
        this.storeName = storeName;
        this.username = username;
    }

    public void update(ProductRequestDto requestDto, User user) {
        this.productName = requestDto.getProductName();
        this.amount = requestDto.getAmount();
        this.price = requestDto.getPrice();
        this.storeName = user.seller();
    }
}