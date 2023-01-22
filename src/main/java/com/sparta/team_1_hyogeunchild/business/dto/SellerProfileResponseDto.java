package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Category;
import com.sparta.team_1_hyogeunchild.persistence.entity.Seller;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class SellerProfileResponseDto {
    private String nickName;
    private String storeName;
    private String image;
    private String introduce;
    private List<Category> categoryList;
    public SellerProfileResponseDto(Seller seller){
        this.nickName= seller.getNickName();
        this.storeName = seller.getStoreName();
        this.image = seller.getImage();
        this.introduce = seller.getIntroduce();
        this.categoryList = seller.getCategories();
    }
}
