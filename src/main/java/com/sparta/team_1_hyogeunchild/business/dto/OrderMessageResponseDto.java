package com.sparta.team_1_hyogeunchild.business.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderMessageResponseDto {
    private String message;

    public OrderMessageResponseDto(String message){
        this.message = message;
    }
}
