package com.sparta.team_1_hyogeunchild.presentation.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class PromoteUserRequestDto {
    private String username;
    private String password;
    @Pattern(regexp = "(?=.*[a-z])^[a-z0-9ㄱ-힣]{2,10}$", message = "최소 2자 이상, 10자 이하이며 한글과 소문자로만 적어주세요.")
    private String storeName;

}
