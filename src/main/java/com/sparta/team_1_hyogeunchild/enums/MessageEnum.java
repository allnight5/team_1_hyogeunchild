package com.sparta.team_1_hyogeunchild.enums;


import lombok.Getter;

@Getter
public enum MessageEnum {

    //------------------------------------------------
    //------------------------------------------------
    //판재마->구매자 구매자->판매자 등급변환 관련
    PROMOTE_BUYER("판매자로의 등급 변환에 성공했습니다. success promote"),
    DEGRADE_SELLER("구매자로 등급 변환에 성공했습니다. success degrade"),

    //------------------------------------------------
    //------------------------------------------------
    //카테고리 관련
    UPDATE_CATEGORY_SUCCESS("카테고리 변경에 성공했습니다. success category update"),
    UPDATE_CATEGORY_FAIL("카테고리 변경에 실패하고 말았습니다. fail category update");

    //------------------------------------------------
    //------------------------------------------------

    private final String message;
    MessageEnum(String message){
        this.message = message;
    }

}
