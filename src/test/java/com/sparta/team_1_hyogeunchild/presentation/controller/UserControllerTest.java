package com.sparta.team_1_hyogeunchild.presentation.controller;

import com.sparta.team_1_hyogeunchild.business.service.UserService;
import com.sparta.team_1_hyogeunchild.presentation.dto.SignUpRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;

    @BeforeEach
    public void init(){
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    // HTTP 환경 세팅을 위해서 @BeforeEach 로 init을 합니다. 목 MVC빌더가 standaloneSetup하기 위함

    // Request, Response Dto들을 먼저 셋업합니다.

    private SignUpRequestDto signUpRequest(){
        return new SignUpRequestDto("username","password",false,"",false);
    }

    @DisplayName("회원가입 테스트")
    @Test
    void signupPage() {
        SignUpRequestDto requestDto = signUpRequest();
    }

    @Test
    void login() {
    }

    @Test
    void delete() {
    }

    @Test
    void promoteUser() {
    }

    @Test
    void deletePromote() {
    }

    @Test
    void getAllSellers() {
    }

    @Test
    void getAllSProducts() {
    }

    @Test
    void createProfile() {
    }
}