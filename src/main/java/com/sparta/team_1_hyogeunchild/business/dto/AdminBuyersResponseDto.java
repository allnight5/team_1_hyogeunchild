package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import lombok.Getter;

@Getter
public class AdminBuyersResponseDto {
    private final Long id;
    private final String username;
    public AdminBuyersResponseDto(User user){
        this.id = user.getId();;
        this.username= user.getUsername();
    }
}
