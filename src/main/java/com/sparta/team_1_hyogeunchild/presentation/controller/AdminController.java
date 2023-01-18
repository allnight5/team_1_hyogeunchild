package com.sparta.team_1_hyogeunchild.presentation.controller;

import com.sparta.team_1_hyogeunchild.business.dto.AdminSellersResponseDto;
import com.sparta.team_1_hyogeunchild.business.dto.AdminPromoteResponseDto;
import com.sparta.team_1_hyogeunchild.business.dto.AdminPromoteShowResponseDto;
import com.sparta.team_1_hyogeunchild.business.dto.AdminBuyersResponseDto;
import com.sparta.team_1_hyogeunchild.business.service.AdminService;
import com.sparta.team_1_hyogeunchild.presentation.dto.AdminPromoteRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//메서드 혹은 클래스 단위로 Mapping을 주어 중복 URL을 공통으로 처리할 수 있다
@RequestMapping("/admin")
// final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    //1.구매자 -> 판매자로 승급
    @PutMapping("/promote")
    public AdminPromoteResponseDto promote(@RequestBody AdminPromoteRequestDto requestDto){
        return adminService.promoteBuyer(requestDto.getUsername());
    }

    //2. 판매자 자격 박탈->구매자
    @PutMapping("/degrade")
    public AdminPromoteResponseDto degrade(@RequestBody AdminPromoteRequestDto requestDto){
        return adminService.degradeSeller(requestDto.getUsername());
    }

//    3. 유저목록조회
    @GetMapping("/buyerList")
    public List<AdminBuyersResponseDto> getBuyer(@PageableDefault Pageable pageable) {
        return adminService.getBuyer(pageable.getPageNumber(), pageable.getPageSize());
    }
//    4. 판매자 목록조회
    @GetMapping("/sellerList")
        public List<AdminSellersResponseDto> getSeller(@PageableDefault Pageable pageable){
        return adminService.getSeller(pageable.getPageNumber(), pageable.getPageSize());
    }

//    5. 등급 업 심사 대기중인 사람들 조회
//public List<AdminPromoteShowResponseDto> waitSeller(@RequestParam("page") int page,
//                                                    @RequestParam("size") int size){

    //@PageableDefault은 위에
    // @RequestParam("page") int page,@RequestParam("size") int size
    // 이라는 내용을 가지고 있는 어노테이션이다.
    @GetMapping("/registe/buyerList")
    public List<AdminPromoteShowResponseDto> waitSeller(@PageableDefault Pageable pageable){
        //number(페이지 번호)만 이용해서찾고싶다면 PageNumber만 보내면 되고
        //size와 같이 보내고 싶다면 size도 추가해서 보내주면된다.
        //postman에서의 적용방식은
        //parms에 key page value 1(key에 page라는 컬럼을 추가해주고 그 내용이 1이라는뜻이다) 이것은 페이지번호만 보냈을때
        //size도 같이 보내서 하겠다면
        //parms에 가서 key (page, size) value (1, 3)형식으로 하면된다.
        //그림으로 보고싶다면 S.A에 올려둘태니 보는것이좋다.
        //여기서 내용은 이게 끝이니 service로 이동하자
        return adminService.getPromoteWaitBuyer(pageable.getPageNumber(), pageable.getPageSize());
    }
}
