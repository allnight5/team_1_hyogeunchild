package com.sparta.team_1_hyogeunchild.presentation.controller;

import com.sparta.team_1_hyogeunchild.business.dto.SellerProfileResponseDto;
import com.sparta.team_1_hyogeunchild.business.service.SellerService;
import com.sparta.team_1_hyogeunchild.presentation.dto.CategoryRequestDto;
import com.sparta.team_1_hyogeunchild.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller/profile")
// final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
@RequiredArgsConstructor
public class SellerProfileController {

    private final SellerService sellerService;
    //1. 카테고리 변경
    @PutMapping("/category")
    public String updateCategory(@RequestBody CategoryRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        List<String> category = requestDto.getCategory();
        return sellerService.updateCategory(category, userDetails.getUser());
    }
    //2. 판매자 프로필 조회
    @GetMapping("/{id}")
    private SellerProfileResponseDto getProfileSeller(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id){
        return sellerService.getProfileSeller(userDetails.getUser(), id);
    }
}
