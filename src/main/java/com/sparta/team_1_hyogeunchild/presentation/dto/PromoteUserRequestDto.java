package com.sparta.team_1_hyogeunchild.presentation.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class PromoteUserRequestDto {
    private String password;
    @Pattern(regexp = "(?=.*[a-z])^[?!a-z0-9가-힣]{2,10}$", message = "최소 2자 이상, 10자 이하이며 한글, 영문, 숫자로만 적어주세요.")
    private String storeName;
    private String username;
    // 실제로 판매자 객체가 생성되기 위해서 요구하는 폼. < 근데, 구매자랑 판매자 중복되면 안됨.
    // 슈퍼타입(유저) - > 서브타입(셀러)
    // 하나가 더필요해요. 왜냐면 delete쿼리가 생성 이전에 날아가요...
    // 리퀘스트 받아오면, 1번 매서드에서 유저 삭제하는 절차를 밟고, 이거를 빈 객체를 생성해서 return(리퍼지토리 세이브 안함)
    // 2번 매서드에서는 받아온 Seller 객체를 save만 해주면 됨. <
}
