package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.business.dto.PromoteResponseDto;
import com.sparta.team_1_hyogeunchild.enums.UserRoleEnum;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.persistence.repository.UserRepository;
import com.sparta.team_1_hyogeunchild.presentation.dto.PromoteRequestDto;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    //4.구매자 -> 판매자로 승급
    @Transactional
    public PromoteResponseDto promoteAuthorization(PromoteRequestDto requestDto, User users){
        return getPromoteResponseDto(requestDto, users, UserRoleEnum.SELLER, "판매자로의 등급 변환에 성공했습니다.");
    }
    //5. 판매자 자격 박탈->구매자
    //buyerAuthorization or promoteLossOfAuthority
    @Transactional
    public PromoteResponseDto promoteLossOfAuthority(PromoteRequestDto requestDto, User users){
        return getPromoteResponseDto(requestDto, users, UserRoleEnum.BUYER, "구매자로 등급 변환에 성공했습니다.");
    }
    //6. 유저목록조회

    //7. 판매자 목록조회

    //8. 등급 업 심사 대기중인 사람들 조회

    //9. (4번과 5번 통합)구매자 -> 판매자로 승급, 판매자 자격 박탈->구매자
    private PromoteResponseDto getPromoteResponseDto(PromoteRequestDto requestDto, User users, UserRoleEnum seller, String message) {
        String storeName = requestDto.getStoreName();
        String username = requestDto.getUsername();
        if(users.getRole().equals(UserRoleEnum.ADMIN) && !requestDto.getUsername().equals(users.getUsername())) {
            User user = userRepository.findByUsername(username).orElseThrow(
                    ()-> new IllegalArgumentException("사용자를 찾을수 없습니다.")
            );
            user.promote(storeName, seller);
            return new PromoteResponseDto(message);
        }
        throw new SecurityException("관리자가 아닙니다.");
    }
}
