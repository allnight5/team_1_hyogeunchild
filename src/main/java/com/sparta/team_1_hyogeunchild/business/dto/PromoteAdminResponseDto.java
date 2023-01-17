package com.sparta.team_1_hyogeunchild.business.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PromoteAdminResponseDto {
    private String message;

    public PromoteAdminResponseDto(String message){
        this.message = message;
    }
}
