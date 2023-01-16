package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.business.dto.CreateResponseDto;
import com.sparta.team_1_hyogeunchild.business.dto.DeleteResponseDto;
import com.sparta.team_1_hyogeunchild.business.dto.LoginResponseDto;
import com.sparta.team_1_hyogeunchild.business.dto.PromoteResponseDto;
import com.sparta.team_1_hyogeunchild.enums.UserRoleEnum;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.persistence.repository.UserRepository;
import com.sparta.team_1_hyogeunchild.presentation.dto.LoginRequestDto;
import com.sparta.team_1_hyogeunchild.presentation.dto.PromoteRequestDto;
import com.sparta.team_1_hyogeunchild.presentation.dto.SignUpRequestDto;
import com.sparta.team_1_hyogeunchild.presentation.dto.UserDeleteRequestDto;
import com.sparta.team_1_hyogeunchild.security.jwt.JwtUtil;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    // 회원가입 로직
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "D1d@A$5dm4&4D1d1i34n%7";
    //1.회원가입
    @Transactional
    public CreateResponseDto signUp(SignUpRequestDto requestDto){
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> found = userRepository.findByUsername(username);
        if(found.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        UserRoleEnum role = UserRoleEnum.BUYER;
        if(requestDto.isAdmin()){
            if(!requestDto.getAdminToken().equals(ADMIN_TOKEN)){
                throw new SecurityException("관리자 암호가 틀렸습니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        User user = new User(username, password, role);
        userRepository.save(user);
        return new CreateResponseDto("회원가입 성공");
    }
    //2.로그인
    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto){
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("사용자를 찾을수 없습니다.")
        );
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("사용자를 찾을수 없습니다.");
        }

        return new LoginResponseDto(jwtUtil.createToken(user.getUsername(), user.getRole()));
    }
    //3.회원탈퇴
    @Transactional
    public DeleteResponseDto deleteUser(UserDeleteRequestDto deleteRequestDto, User user){
        //앞에는 지금 로그인한 유저가 게시글 작성한 유저와 같은지 검사함
        //뒤에는 지금이 로그인한사람이 유저인지 관리자인지 검사함
        if (user.getRole().equals(UserRoleEnum.ADMIN) ||
                user.getUsername().equals(deleteRequestDto.getUsername())&&
                        passwordEncoder.matches(deleteRequestDto.getPassword(),user.getPassword())) {
            //DB에서 삭제처리를 해줌
            userRepository.deleteByUsername(deleteRequestDto.getUsername());
            return new DeleteResponseDto("삭제 성공");
        }
        throw new SecurityException("가입한 회원만이 탈퇴할 수 있습니다");
    }
    //3.구매자 -> 판매자로 승급
    @Transactional
    public PromoteResponseDto promoteUp(PromoteRequestDto requestDto){

        return new PromoteResponseDto("판매자로의 등급 변환에 성공했습니다.");
    }
    //4. 판매자 자격 박탈->구매자

    //5. 유저목록조회

    //6. 판매자 목록조회

    //7. 등급 업 심사 대기중인 사람들 조회
}
