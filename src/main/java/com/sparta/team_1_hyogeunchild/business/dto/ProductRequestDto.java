package com.sparta.team_1_hyogeunchild.business.dto;

import lombok.Getter;

import javax.persistence.Column;
@Getter
public class ProductRequestDto {
    private String productName;
    private Long amount;
    private Long price;

}
