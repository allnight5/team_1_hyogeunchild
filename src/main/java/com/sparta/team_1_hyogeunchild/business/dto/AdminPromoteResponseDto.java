package com.sparta.team_1_hyogeunchild.business.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminPromoteResponseDto {
    private String message;

    public AdminPromoteResponseDto(String message){
        this.message = message;
    }
}
