package com.sparta.team_1_hyogeunchild.persistence.entity;

import com.sparta.team_1_hyogeunchild.business.dto.ProductRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    @ManyToOne
    @JoinColumn(name = "User_Id")
    private User users;

    public Product(ProductRequestDto requestDto, User user) {
        this.productName = requestDto.getProductName();
        this.price = requestDto.getPrice();
        this.amount = requestDto.getAmount();
        this.storeName = requestDto.getStoreName();
        this.users = user;
    }


    public void update(ProductRequestDto requestDto) {
        this.productName = requestDto.getProductName();
        this.amount = requestDto.getAmount();
        this.price = requestDto.getPrice();
        this.storeName = requestDto.getStoreName();
    }
}