package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Category;
import com.sparta.team_1_hyogeunchild.persistence.entity.Seller;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SellerProfileResponseDto {
    private final String nickName;
    private final String storeName;
    private final String image;
    private final String introduce;
    private final List<CategoryResponseDto> categoryList;
    private SellerProfileResponseDto(Seller seller){
        this.nickName= seller.getNickName();
        this.storeName = seller.getStoreName();
        this.image = seller.getImage();
        this.introduce = seller.getIntroduce();
        this.categoryList = seller.getCategories().stream().map(CategoryResponseDto::from).collect(Collectors.toList());
    }
    public static SellerProfileResponseDto from(Seller seller){
        return new SellerProfileResponseDto(seller);
    }
}
