package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Product;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private String productName;
    private Long amount;
    // 상품 양(남은 개수)
    private Long price;
    private String storeName;

    public ProductResponseDto(Product product, User user) {
        this.productName = product.getProductName();
        this.amount = product.getAmount();
        this.price = product.getPrice();
        this.storeName = user.getStoreName();
    }


//    public static ProductResponseDto from(Product product){
//        return new ProductResponseDto(product);
//    }
}
