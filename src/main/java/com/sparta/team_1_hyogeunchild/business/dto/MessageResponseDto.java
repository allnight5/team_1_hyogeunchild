package com.sparta.team_1_hyogeunchild.business.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageResponseDto {
    private String message;

    public MessageResponseDto(String message){
        this.message = message;
    }
}
