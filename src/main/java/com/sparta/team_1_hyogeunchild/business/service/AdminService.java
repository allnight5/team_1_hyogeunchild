package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.business.dto.PromoteAdminResponseDto;
import com.sparta.team_1_hyogeunchild.enums.MessageEnum;
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
    public PromoteAdminResponseDto promoteBuyer(String username){
        return getPromoteResponseDto(username, UserRoleEnum.SELLER);
    }
    //2. 판매자 자격 박탈->구매자
    //buyerAuthorization or promoteLossOfAuthority
    @Transactional
    public PromoteAdminResponseDto degradeSeller(String username){
        return getPromoteResponseDto(username, UserRoleEnum.BUYER);
    }
    //3. 유저 목록 조회

    //4. 판매자 목록 조회

    //5. 등급 업 심사 대기중인 사람들 조회

    //6. (4번과 5번 통합)구매자 -> 판매자로 승급, 판매자 자격 박탈->구매자
    private PromoteAdminResponseDto getPromoteResponseDto(String username, UserRoleEnum role) {
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("사용자를 찾을수 없습니다.")
        );
        Promote promote = promoteRepository.findByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("판매자 권한을 신청하는 유저가 아닙니다.")
        );

        if(!user.getRole().equals(UserRoleEnum.ADMIN)){
            if(user.getRole().equals(UserRoleEnum.BUYER)){
                promoteRepository.delete(promote);
                user.promote(promote.getStoreName(), role);
                return new PromoteAdminResponseDto(MessageEnum.PROMOTE_BUYER.getMessage());
            }
            user.promote(null, role);
            return new PromoteAdminResponseDto(MessageEnum.DEGRADE_SELLER.getMessage());
        }
        throw new SecurityException("관리자의 권한을 변경할수없습니다..");
    }
}
