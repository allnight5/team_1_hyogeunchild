package com.sparta.team_1_hyogeunchild.presentation.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Getter
@NoArgsConstructor
public class OrderRequestDto {
    // Order 하는 품목, price 곱해서...아니다. < product amount 만 하면 price 는 알아서 계산되게?
    private Long amount;
}