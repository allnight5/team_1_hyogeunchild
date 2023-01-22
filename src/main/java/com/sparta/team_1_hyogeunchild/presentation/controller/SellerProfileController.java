package com.sparta.team_1_hyogeunchild.presentation.controller;

import com.sparta.team_1_hyogeunchild.business.dto.MessageResponseDto;
import com.sparta.team_1_hyogeunchild.business.dto.SellerProfileResponseDto;
import com.sparta.team_1_hyogeunchild.business.dto.SellerResponseDto;
import com.sparta.team_1_hyogeunchild.business.service.SellerService;
import com.sparta.team_1_hyogeunchild.persistence.entity.Seller;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.presentation.dto.CategoryRequestDto;
import com.sparta.team_1_hyogeunchild.security.service.UserDetailsImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
// final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
@RequiredArgsConstructor
public class SellerProfileController {
    private final SellerService sellerService;
    //1. 카테고리 변경
    @PostMapping("/category")
    @PreAuthorize("hasRole('SELLER')")
    public String createCategory(@RequestBody CategoryRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return sellerService.createCategory(requestDto, (Seller) userDetails.getUser());
        // 여기서 캐스팅을 한 건 가능하다는 것을 보여드리기 위함입니다.
        // userDetails 의 userId를 가져와서 findById를 해도 됩니다. DB에 한번 더 들러야 한다는 단점이 있습니다.
    }
    //2. 카테고리 삭제
    @DeleteMapping("/category/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public String deleteCategory(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return sellerService.deleteCategory(id, (Seller) userDetails.getUser());
    }
    // 3. 판매자 삭제
    @DeleteMapping("/{id}")
    public MessageResponseDto deleteSeller(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return sellerService.deleteSeller(id, (Seller) userDetails.getUser());
    }




    //2. 판매자 프로필 조회
//    @GetMapping("/{id}")
//    private SellerProfileResponseDto getProfileSeller(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id){
//        return sellerService.getProfileSeller(userDetails.getUser(), id);
//    }
//    //카테고리 삭제
//    @DeleteMapping("/delete/{id}")
//    public String deleteCategory(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
//    return sellerService.deleteCategory(id,userDetails.getUser());
//    }
}
