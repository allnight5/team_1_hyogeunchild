package com.sparta.team_1_hyogeunchild.business.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PromoteResponseDto {
    private String message;

    public PromoteResponseDto(String message){
        this.message = message;
    }
}
