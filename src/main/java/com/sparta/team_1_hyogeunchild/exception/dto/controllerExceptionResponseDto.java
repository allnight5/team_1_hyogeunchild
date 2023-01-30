package com.sparta.team_1_hyogeunchild.exception.dto;

import lombok.Getter;

@Getter
public class controllerExceptionResponseDto {
    private String errorMessage;
    private int Status;

    public controllerExceptionResponseDto(String errorMessage, int Status){
        this.errorMessage = errorMessage;
        this.Status = Status;
    }
}
