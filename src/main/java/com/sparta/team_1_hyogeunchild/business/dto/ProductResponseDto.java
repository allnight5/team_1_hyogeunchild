package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Product;

import javax.persistence.Column;

public class ProductResponseDto {

    private String productName;
    private Long amount;
    // 상품 양(남은 개수)
    private Long price;
    private String storeName;

    private ProductResponseDto(Product product) {
        this.productName = product.getProductName();
        this.amount = product.getAmount();
        this.price = product.getPrice();
        this.storeName = product.getStoreName();
    }

    public static ProductResponseDto from(Product product){
        return new ProductResponseDto(product);
    }
}
