package com.sparta.team_1_hyogeunchild.business.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateResponseDto {
    private String message;

    public CreateResponseDto(String message){
        this.message = message;
    }
}
