package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Seller;
import lombok.Getter;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;

@Getter
public class SellerResponseDto {
    private final String storeName;
    private final String category;
    private final String introduce;

    private SellerResponseDto(Seller seller) {
        this.storeName = seller.getStoreName();
        this.category = seller.getCategory();
        this.introduce = seller.getIntroduce();
    }
    public static SellerResponseDto from(Seller seller) {
        return new SellerResponseDto(seller);
    }
}
