package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Category;
import com.sparta.team_1_hyogeunchild.persistence.entity.Seller;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SellerResponseDto {
    private final String storeName;
    private final String introduce;
    private final List<CategoryResponseDto> categoryList;

    private SellerResponseDto(Seller seller) {
        this.storeName = seller.getStoreName();
        this.introduce = seller.getIntroduce();
        this.categoryList = seller.getCategories().stream().map(CategoryResponseDto::from).collect(Collectors.toList());
    }
    public static SellerResponseDto from(Seller seller) {
        return new SellerResponseDto(seller);
    }
}
