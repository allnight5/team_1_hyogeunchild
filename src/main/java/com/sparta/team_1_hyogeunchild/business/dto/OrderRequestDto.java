package com.sparta.team_1_hyogeunchild.business.dto;

import lombok.Getter;

@Getter
public class OrderRequestDto {
    private Long amount;
    private Long available = 0L;

}
