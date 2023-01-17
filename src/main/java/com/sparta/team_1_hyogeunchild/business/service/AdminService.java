package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.business.dto.PromoteAdminResponseDto;
import com.sparta.team_1_hyogeunchild.enums.UserRoleEnum;
import com.sparta.team_1_hyogeunchild.persistence.entity.Promote;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.persistence.repository.PromoteRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.UserRepository;
import com.sparta.team_1_hyogeunchild.presentation.dto.PromoteAdminRequestDto;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PromoteRepository promoteRepository;
    //1.구매자 -> 판매자로 승급
    @Transactional
    public PromoteAdminResponseDto promoteAuthorization(PromoteAdminRequestDto requestDto){
        String message = "판매자로의 등급 변환에 성공했습니다.";
        return getPromoteResponseDto(requestDto, UserRoleEnum.SELLER, message);
    }
    //2. 판매자 자격 박탈->구매자
    //buyerAuthorization or promoteLossOfAuthority
    @Transactional
    public PromoteAdminResponseDto promoteLossOfAuthority(PromoteAdminRequestDto requestDto){
        String message ="구매자로 등급 변환에 성공했습니다.";
        return getPromoteResponseDto(requestDto, UserRoleEnum.BUYER, message);
    }
    //3. 유저 목록 조회

    //4. 판매자 목록 조회

    //5. 등급 업 심사 대기중인 사람들 조회

    //6. (4번과 5번 통합)구매자 -> 판매자로 승급, 판매자 자격 박탈->구매자
    private PromoteAdminResponseDto getPromoteResponseDto(PromoteAdminRequestDto requestDto, UserRoleEnum seller, String message) {
        String username = requestDto.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("사용자를 찾을수 없습니다.")
        );
        Promote promote = promoteRepository.findByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("판매자 권한을 신청하는 유저가 아닙니다.")
        );
        if(!user.getRole().equals(UserRoleEnum.ADMIN)){
            if(user.getRole().equals(UserRoleEnum.SELLER)){
                promoteRepository.delete(promote);
                user.promote(null, seller);
                return new PromoteAdminResponseDto(message);
            }
            user.promote(promote.getStoreName(), seller);
            return new PromoteAdminResponseDto(message);
        }
        throw new SecurityException("관리자의 권한을 변경할수없습니다..");
    }
}
