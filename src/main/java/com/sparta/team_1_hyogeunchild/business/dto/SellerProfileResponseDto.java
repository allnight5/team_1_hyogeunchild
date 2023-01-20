package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SellerProfileResponseDto {
    private String nickName;
    private String storeName;
    private String image;
    private String introduce;
    private List<Category> categoryList;
    public SellerProfileResponseDto(String nickName, String storeName, String image,
                                    String introduce, List<Category> categoryList){
        this.nickName= nickName;
        this.storeName = storeName;
        this.image = image;
        this.introduce =introduce;
        this.categoryList = categoryList;
    }
}
