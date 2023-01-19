package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Seller;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import lombok.Getter;

@Getter
public class AdminSellersResponseDto {

    private final Long id;
    private final String username;
    private final String storeName;
    public AdminSellersResponseDto(Seller seller){
        this.id = seller.getId();;
        this.username= seller.getUsername();
        this.storeName = seller.getStoreName();
    }
}
