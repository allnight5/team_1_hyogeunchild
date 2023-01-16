package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Order;
import com.sparta.team_1_hyogeunchild.persistence.entity.Product;

public class OrderResponseDto {

    private Long totalPrice;

    private Long amount;

    private Product product;

    private OrderResponseDto(Order order) {
        this.totalPrice = order.getTotalPrice();
        this.amount = order.getAmount();
        this.product = order.getProduct();
    }

    public static OrderResponseDto from(Order order) {
        return new OrderResponseDto(order);
    }
}
