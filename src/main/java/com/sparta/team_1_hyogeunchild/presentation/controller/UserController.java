package com.sparta.team_1_hyogeunchild.presentation.controller;
import com.sparta.team_1_hyogeunchild.business.dto.*;
import com.sparta.team_1_hyogeunchild.business.service.UserService;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.presentation.dto.*;
import com.sparta.team_1_hyogeunchild.security.jwt.JwtUtil;
import com.sparta.team_1_hyogeunchild.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.result.Output;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
//메서드 혹은 클래스 단위로 Mapping을 주어 중복 URL을 공통으로 처리할 수 있다
@RequestMapping("/users")
// final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    //1.회웝가입
    //POST 메소드는 주로 새로운 리소스를 생성(create)할 때 사용. 서버에 데이터 추가 or 작성시

    //@RequestBody -> 클라이언트에서 JSON 데이터를 요청 본문에 담아서 서버로 보내면,
    //서버에서는 @RequestBody 어노테이션을 사용하여 HTTP 요청 본문에 담긴 값들을 자바객체로 변환시켜, 객체에 저장한다.
    //@Valid
    //->@RequestBody 어노테이션 옆에 @Valid를 작성하면, RequestBody로 들어오는 객체에 대한 검증을 수행한다.
    // 이 검증의 세부적인 사항은 객체 안에 정의를 해두어야 한다.ex)정규표현식
    @PostMapping("/signup")
    public CreateResponseDto signupPage(@RequestBody @Valid SignUpRequestDto signupRequestDto) {
        return userService.signUp(signupRequestDto);
    }
    //2.로그인
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        //이름과 유저인지 관리자인지 구분한 토큰을 가져오는 부분
        LoginResponseDto msg = userService.login(loginRequestDto);
        //문자열 token에 가져온 정보를 넣어주는 부분
        String token = msg.getMessage();
        //헤더를 통해 토큰을 발급해 주는 부분
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        return new LoginResponseDto("로그인 되었습니다.");
    }
    //3.유저 삭제
    @DeleteMapping("/delete")
    //@AuthenticationPrincipal -> 세션 정보 UserDetails에 접근할 수 있는 어노테이션
    //현재 로그인한 사용자 객체를 가져오기 위해 필요
    public DeleteResponseDto delete(@RequestBody UserDeleteRequestDto deleteRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.deleteUser(deleteRequestDto, userDetails.getUser());
    }
    //4. 판매자로 요청
    @PostMapping("/promote")
    @PreAuthorize("hasRole('BUYER')")
    public PromoteUserResponseDto promoteUser(@RequestBody @Valid PromoteUserRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.promoteUser(requestDto, userDetails.getUser());
    }
    //4-1. 판매자 요청 승인 전 취소
    @DeleteMapping("/promote")
    @PreAuthorize("hasRole('BUYER')")
    public String deletePromote(@AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.deletePromote(userDetails.getUser());
        return "삭제 완료되었습니다.";
    }
    //5. 판매자 목록조회
    @GetMapping("/sellerlist")
    public List<UserResponseDto> getAllSellers(@PageableDefault Pageable pageable){
        return userService.getAllSellers(pageable.getPageNumber());
    }
    //6. 판매 상품 목록 조회
    @GetMapping("/products")
    public List<ProductResponseDto> getAllSProducts(@PageableDefault Pageable pageable){
        return userService.getAllProducts(pageable.getPageNumber());
    }
    //7.유저 프로필 이미지 추가
    @PostMapping("/profile")
    public String createProfile(
            @RequestPart("file") MultipartFile file,
            @RequestPart("nickName") ProfileRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){

        return userService.createProfile(file, requestDto, userDetails.getUser());
    }

    //8.유저 프로필 이미지 변경

    //9.유저 프로필 이미지 불러오기
    @GetMapping(value = "image/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Integer> userSearch(@PathVariable("imagename") String imagename) throws IOException {
        InputStream imageStream = new FileInputStream("C:/Users/mind/sparta_java/team_1_hyogeunchild/src/main/resources/static/image/" + imagename);
        byte[] bytes = {110, 101, 120, 116, 115, 116, 101, 112};
        OutputStream outputStream = new ByteArrayOutputStream(bytes.length);
        int imageByteArray = IOUtils.copy(imageStream, outputStream);
        imageStream.close();
        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }
//    @GetMapping("/profile/{fileName}")
//    public ResponseEntity<Resource> getProfile(@PathVariable("fileName") String fileName) throws FileNotFoundException {
//        try{
//            String path = "/Users/mind/sparta_java/team_1_hyogeunchild/src/main/resources/static/image/";
//            FileSystemResource resource = new FileSystemResource(path+fileName);
//            if (!resource.exists()) {
//                throw new FileNotFoundException();
//            }
//            HttpHeaders header = new HttpHeaders();
//            Path filePath = null;
//            filePath = Paths.get(path+fileName);
//            header.add("Content-Type", Files.probeContentType(filePath));
//            return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
//        }catch (Exception e) {
//            throw new FileNotFoundException();
//        }
//
//    }


}
