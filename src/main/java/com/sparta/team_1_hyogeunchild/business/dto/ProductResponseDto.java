package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Product;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private String productName;
    private String username;
    private Long price;
    private String storeName;

    public ProductResponseDto(Product product) {
        this.productName = product.getProductName();
        this.username = product.getUsername();
        this.storeName = product.getStoreName();
        this.price = product.getPrice();
    }


//    public static ProductResponseDto from(Product product){
//        return new ProductResponseDto(product);
//    }
}
