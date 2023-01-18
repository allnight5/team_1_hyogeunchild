package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private Long id;
    private Long totalPrice;
    private Long amount;
    private Long productId;
    private String productName;
    private String storeName;
    private Long price;

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.amount = order.getAmount();
        this.productId = order.getProduct().getId();
        this.productName = order.getProduct().getProductName();
        this.storeName = order.getProduct().getStoreName();
        this.price = order.getProduct().getPrice();
    }

    public static OrderResponseDto from(Order order) {
        return new OrderResponseDto(order);
    }
}
