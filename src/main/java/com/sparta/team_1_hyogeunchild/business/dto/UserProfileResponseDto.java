package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Seller;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserProfileResponseDto {
    private final String nickName;
    private final String image;
    private UserProfileResponseDto(User user){
        this.nickName= user.getNickName();
        this.image = user.getImage();
    }
    public static UserProfileResponseDto from(User user){
        return new UserProfileResponseDto(user);
    }
}
