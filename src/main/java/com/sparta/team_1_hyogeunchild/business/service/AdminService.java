package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.business.dto.AdminPromoteResponseDto;
import com.sparta.team_1_hyogeunchild.business.dto.AdminPromoteShowResponseDto;
import com.sparta.team_1_hyogeunchild.enums.MessageEnum;
import com.sparta.team_1_hyogeunchild.enums.UserRoleEnum;
import com.sparta.team_1_hyogeunchild.persistence.entity.Promote;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.persistence.repository.PromoteRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.UserRepository;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PromoteRepository promoteRepository;
    //1.구매자 -> 판매자로 승급
    @Transactional
    public AdminPromoteResponseDto promoteBuyer(String username){
        return getPromoteResponseDto(username, UserRoleEnum.SELLER);
    }
    //2. 판매자 자격 박탈->구매자
    //buyerAuthorization or promoteLossOfAuthority
    @Transactional
    public AdminPromoteResponseDto degradeSeller(String username){
        return getPromoteResponseDto(username, UserRoleEnum.BUYER);
    }
    //3. 유저 목록 조회

    //4. 판매자 목록 조회

    //5. 등급 업 심사 대기중인 사람들 조회
    //List로 감싸준 이유는 어쨋든 보내주거나하려면 List에 넣어서 보내주기때문에
    //처음부터 감싸서 만든것이다.
    @Transactional
    public List<AdminPromoteShowResponseDto> getPromoteWaitBuyer(int page, int size){
        //분리해주었다 아래 메소드 pageableSetting에 가고싶은 페이지 번호와 한페이지에 보고싶은 갯수를 보내준다.
        Pageable pageable = pageableSetting(page, size);
        //Page객체안에 promote라는 제네릭스를 넣고 wait라는 변수명으로 생성후
        //모든 파일을 찾아서 가져오는데 위에 pageable에서 정한 기준을 따라서 가져오게 된다.
        Page<Promote> wait = promoteRepository.findAll(pageable);

        //wait를 stream을 이용하여 반복문을 돌리는데 map을 이용해서 하는데.
        // 나도모름 stream.map검색추천 공부하는데 설명못함 ㅇ.ㅇ
        return wait.stream().map(AdminPromoteShowResponseDto::new).collect(Collectors.toList());

        //저장소내용이다.

        //기준없이 전부다 가져온다.
//        Page<Promote> findAll(Pageable pageable);
        //전부다 가져오는것이아닌 내가 보고싶은 내용만 찾아서 가져온다
        //데이터 낭비가적다.
//        Page<Promote> findByUsername(String username, int i , Pageable pageable);
    }

    //5번 페이징 처리 분리
    //page는 가고싶은 페이지번호 size는 한페이지에 보고싶은 갯수이다.
    public Pageable pageableSetting(int page, int size){
        //sort에 어떤 방식으로 정렬을 할것인가를 넣는다. ASC오름차순 정렬인가, DESC내림차순 정렬인가

        Sort.Direction direction = Sort.Direction.ASC;
        //위의 정렬방식을 가져와서 이제 어떤것을 기준으로 정렬할것인가
        //(direction, "id") 앞부분 direction가 정렬방식이고 "id"부분이 기준점이된다.
        // 만약 최초 생성일 마지막 생성일로 바꾸고 싶다면 생성실"createdDate" 수정일"modifiedDate"로 넣어주면된다.

        Sort sort = Sort.by(direction, "id");

        //이제 Pageable객체 형태로 돌려주게 되는데
        //파라미터값을 처음부터 말하면
        //들어온 가고싶은 페이지번호(page-1) 어째서 -1을 넣어주느냐? default가 0이라서 1이라는 페이지를 가고싶다면
        // -1을 해줘야 0번으로 가기때문에 -1을 하는것이다 간단히 0번째가 1번페이지이다
        // 두번째 size의 경우 몇개나 한페이지에 보여주거나 보고싶은가이다
        // size가 3이면 3개를 10개면 DB에 들어있는것중 10개를 보여주거나 보겠다는 뜻이다
        // sort의 경우, 위에서 만든 정렬 방식이다 어떠한 것을 기준으로 정렬할것인지 하는것이다.
        return PageRequest.of(page-1, size, sort);
    }

    //6. (4번과 5번 통합)구매자 -> 판매자로 승급, 판매자 자격 박탈->구매자
    private AdminPromoteResponseDto getPromoteResponseDto(String username, UserRoleEnum role) {
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
                return new AdminPromoteResponseDto(MessageEnum.PROMOTE_BUYER.getMessage());
            }
            user.promote(null, role);
            return new AdminPromoteResponseDto(MessageEnum.DEGRADE_SELLER.getMessage());
        }
        throw new SecurityException("관리자의 권한을 변경할수없습니다..");
    }
}
