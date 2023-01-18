package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Order;
import com.sparta.team_1_hyogeunchild.persistence.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private Long totalPrice;
    private Long amount;
    private Long productId;
    private String productName;
    private String storeName;
    private Long price;

    private OrderResponseDto(Order order) {
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
