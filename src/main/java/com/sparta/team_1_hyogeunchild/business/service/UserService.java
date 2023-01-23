package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.business.dto.*;
import com.sparta.team_1_hyogeunchild.enums.UserRoleEnum;
import com.sparta.team_1_hyogeunchild.persistence.entity.Product;
import com.sparta.team_1_hyogeunchild.persistence.entity.Promote;
import com.sparta.team_1_hyogeunchild.persistence.entity.Seller;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.persistence.repository.ProductRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.PromoteRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.SellerRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.UserRepository;
import com.sparta.team_1_hyogeunchild.presentation.dto.*;
import com.sparta.team_1_hyogeunchild.security.jwt.JwtUtil;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String ADMIN_TOKEN = "D1d@A$5dm4&4D1d1i34n%7";
    // 회원가입 로직
    private final UserRepository userRepository;
    private final PromoteRepository promoteRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final FileService fileService;

    //1.회원가입
    @Transactional
    public MessageResponseDto signUp(SignUpRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickName = requestDto.getNickName();

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        UserRoleEnum role = UserRoleEnum.BUYER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new SecurityException("관리자 암호가 틀렸습니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        User user = new User(username, password, role, nickName);
        userRepository.save(user);
        return new MessageResponseDto("회원가입 성공");
    }

    //2.로그인
    @Transactional
    public MessageResponseDto login(LoginRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new SecurityException("사용자를 찾을수 없습니다.")
        );
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new SecurityException("사용자를 찾을수 없습니다.");
        }

        return new MessageResponseDto(jwtUtil.createToken(user.getUsername(), user.getRole()));
    }

    //3.회원탈퇴
    @Transactional
    public MessageResponseDto deleteUser(UserDeleteRequestDto deleteRequestDto, User user) {
        //앞에는 지금 로그인한 유저가 게시글 작성한 유저와 같은지 검사함
        //뒤에는 지금이 로그인한사람이 유저인지 관리자인지 검사함
        if (user.getRole().equals(UserRoleEnum.ADMIN) ||
                user.getUsername().equals(deleteRequestDto.getUsername()) &&
                        passwordEncoder.matches(deleteRequestDto.getPassword(), user.getPassword())) {
            //DB에서 삭제처리를 해줌
            userRepository.deleteByUsername(deleteRequestDto.getUsername());
            return new MessageResponseDto("삭제 성공");
        }
        throw new SecurityException("가입한 회원만이 탈퇴할 수 있습니다.");
    }

    //4. 판매 상품 조회
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts(int pageChoice) {
        Page<Product> products = productRepository.findAll(pageableProductsSetting(pageChoice));
        return products.stream().map(ProductResponseDto::new).collect(Collectors.toList());
    }

    private Pageable pageableProductsSetting(int pageChoice) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        return PageRequest.of(pageChoice - 1, 10, sort);
    }


    //5. 판매자 조회
    @Transactional(readOnly = true)
    public List<SellerResponseDto> getAllSellers(int pageChoice) {
        Page<Seller> sellers = sellerRepository.findAll(pageableSellersSetting(pageChoice));
        return sellers.stream().map(SellerResponseDto::from).collect(Collectors.toList());

    }

    private Pageable pageableSellersSetting(int pageChoice) {
        Sort.Direction direction = Sort.Direction.ASC;
        Sort sort = Sort.by(direction, "username");
        return PageRequest.of(pageChoice - 1, 10, sort);
    }

    //6. 판매자 전환 폼 요청
    @Transactional
    public PromoteUserResponseDto promoteUser(PromoteUserRequestDto requestDto, User user) {
        if (promoteRepository.findByUserUsername(user.getUsername()).isPresent()) {
            throw new SecurityException("이미 판매자 전환 요청을 하였습니다.");
        }
        Promote promote = new Promote(requestDto, user);
        promoteRepository.save(promote);
        return new PromoteUserResponseDto(promote);
    }


    //6-1. 판매자 폼 취소
    @Transactional
    public void deletePromote(User user) {
        User user1 = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("유저가 없습니다.")
        );

        Promote promote = promoteRepository.findByUserUsername(user1.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("판매자 요청을 하지 않았습니다.")
        );
        promoteRepository.deleteByUserUsername(promote.getUser().getUsername());
    }

    //7. 유저 프로필 생성
    @Transactional
    public String createProfile(MultipartFile file, ProfileRequestDto requestDto, User user){
        String saveLocal = "local";
        String saveImageName = user.getUsername();
        String updateImageData= saveLocal+saveImageName;
        user.changeProfile(requestDto.getNickName(), updateImageData);
        fileService.upload(file, saveImageName);
        userRepository.save(user);
        return "생성이 완료되었습니다.";
    }
    // AWS S2 < 요거 써서 이미지용 DB 만드는데... 이거 아니니까 노상관?
    // 왜냐면 서버가 직접 로컬에 저장할일이 없을 듯 하여서?
    // 그래서 그냥 remote 링크만 사용자에게 저장하고.
    @Transactional
    public SellerResponseDto getSeller(Long id){
        Seller seller = sellerRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("찾으시는 판매자가 없습니다")
        );
        return SellerResponseDto.from(seller);
    }
}