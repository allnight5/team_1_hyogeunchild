package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.business.dto.*;
import com.sparta.team_1_hyogeunchild.enums.MessageEnum;
import com.sparta.team_1_hyogeunchild.enums.UserRoleEnum;
import com.sparta.team_1_hyogeunchild.persistence.entity.Promote;
import com.sparta.team_1_hyogeunchild.persistence.entity.Seller;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.persistence.repository.PromoteRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.SellerRepository;
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
    private final SellerRepository sellerRepository;
    //0. 유저 승급 체커

    @Transactional
    public List<PromoteUserResponseDto> getPromoteList(int page, int size) {
        Pageable pageable = pageableSetting(page, size);
        Page<Promote> promotes = promoteRepository.findAll(pageable);
        return promotes.stream().map(PromoteUserResponseDto::new).collect(Collectors.toList());
        // 어드민이 모든 promote 들을 조회하는 매서드입니다.
    }

    //1.구매자 -> 판매자로 승급
    @Transactional
    public AdminPromoteResponseDto promoteBuyer(Long promoteId){
        Promote promote = promoteRepository.findById(promoteId).orElseThrow(
                () -> new IllegalArgumentException("잘못된 승급신청 번호입니다.")
        );
            Seller seller = Seller.builder()
                    .storeName(promote.getStoreName())
                    .introduce(promote.getIntroduce())
                    .category(promote.getCategory())
                    .password(promote.getUser().getPassword())
                    .role(UserRoleEnum.SELLER)
                    .username(promote.getNewName())
                    .build();

            sellerRepository.save(seller);
        return new AdminPromoteResponseDto("판매자로 승급 신청이 승인되었습니다.");
    }
    //2. 판매자 자격 박탈->구매자
    //buyerAuthorization or promoteLossOfAuthority
    @Transactional
    public AdminPromoteResponseDto degradeSeller(Long promoteId){
        Promote promote = promoteRepository.findById(promoteId).orElseThrow(
                () -> new IllegalArgumentException("잘못된 승급신청 번호입니다.")
        );
        // Buyer 만 승급신청을 할 수 있으므로, User/Promote 객체의 체커로직은 불필요합니다.

        Seller seller = Seller.builder()
                .storeName(promote.getStoreName())
                .introduce(promote.getIntroduce())
                .password(promote.getUser().getPassword())
                .role(promote.getUser().getRole())
                .username(promote.getUser().getUsername())
                .build();

        sellerRepository.save(seller);
        userRepository.deleteByUsername(promote.getUser().getUsername());
        return new AdminPromoteResponseDto("판매자로 승급 신청이 승인되었습니다.");
    }
    //3. 유저 목록 조회
    @Transactional
    public List<AdminBuyersResponseDto> getBuyer(int page, int size){
        Pageable pageable = pageableSetting(page, size);
        Page<User> user = userRepository.findByRole(UserRoleEnum.BUYER, pageable);
        return user.stream().map(AdminBuyersResponseDto::new).collect(Collectors.toList());
    }
    //4. 판매자 목록 조회
    @Transactional
    public List<SellerResponseDto> getSeller(int page, int size){
        Pageable pageable = pageableSetting(page, size);

        Page<Seller> sellers = sellerRepository.findAll(pageable);


        return sellers.stream().map(SellerResponseDto::from).collect(Collectors.toList());
    }
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
        //AdminPromoteShowResponseDto::new는 생성자 메서드 참조로
        //메서드로 구현한다면 아래와같다.
        //AdminPromoteShowResponseDto DtoResponseDto(int i, String j, String s){
        //      return new AdminPromoteShowResponseDto(i,j,s);
        //}
        //위의 메서드를 (i, j, s) -> new AdminPromoteShowResponseDto(i,j,s)람다로 표현하면 이렇게 되며;
        //람다를 생서자의 메서드 참조로 바꾸면 AdminPromoteShowResponseDto::new이 되는것이다.
        return wait.stream().map(AdminPromoteShowResponseDto::new).collect(Collectors.toList());
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

}
