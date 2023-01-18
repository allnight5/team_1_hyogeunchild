package com.sparta.team_1_hyogeunchild.business.dto;

import lombok.Getter;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;

@Getter
public class UserResponseDto {
    private final String username;

    private final String storeName;


    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.storeName = user.getStoreName();
    }
}
