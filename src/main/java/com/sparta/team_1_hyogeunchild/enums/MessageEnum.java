package com.sparta.team_1_hyogeunchild.enums;


import lombok.Getter;

@Getter
public enum MessageEnum {
    PROMOTE_BUYER("판매자로의 등급 변환에 성공했습니다."),
    DEGRADE_SELLER("구매자로 등급 변환에 성공했습니다.");
    private final String message;
    MessageEnum(String message){
        this.message = message;
    }

}
