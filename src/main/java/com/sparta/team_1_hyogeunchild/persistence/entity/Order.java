package com.sparta.team_1_hyogeunchild.persistence.entity;

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

    @ManyToOne()
    @JoinColumn(name = "Product_ID")
    private Product product;

    // cascade REMOVE? / detach? 걸긴 걸어야함 -> 거절 하면, 상품들이 다 제자리로 돌아가야 하잖아. 근데 remove 되는 게 아니라, 돌아 가야 하잖아. <

    // order -> 모래 만 10만개 10억 해가지고 들어왔어. 근데 사실 구매자는 이 판매자가 파는 모래랑 츄르를 같이 사고 싶었어!
    // order 에서는 그런 요청을 처리할 능력이 없었어. <- 기부니가 안좋다.
}