package com.sparta.team_1_hyogeunchild.presentation.controller;

import com.sparta.team_1_hyogeunchild.business.dto.PromoteResponseDto;
import com.sparta.team_1_hyogeunchild.business.service.AdminService;
import com.sparta.team_1_hyogeunchild.presentation.dto.PromoteRequestDto;
import com.sparta.team_1_hyogeunchild.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
//메서드 혹은 클래스 단위로 Mapping을 주어 중복 URL을 공통으로 처리할 수 있다
@RequestMapping("/admin")
// final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    //1.구매자 -> 판매자로 승급
    @PutMapping("/promote")
    public PromoteResponseDto promote(@RequestBody PromoteRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return adminService.promoteAuthorization(requestDto, userDetails.getUser());
    }

    //2. 판매자 자격 박탈->구매자
    @PutMapping("/degrade")
    public PromoteResponseDto degrade(@RequestBody PromoteRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return adminService.promoteLossOfAuthority(requestDto, userDetails.getUser());
    }

//    3. 유저목록조회
//    @GetMapping("/buyerlist")

//    4. 판매자 목록조회
//    @GetMapping("/sellerlist")

//    5. 등급 업 심사 대기중인 사람들 조회
//    @GetMapping("/registe/seller")
}
