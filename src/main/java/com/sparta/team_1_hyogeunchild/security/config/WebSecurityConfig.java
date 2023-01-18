package com.sparta.team_1_hyogeunchild.security.config;

import com.sparta.team_1_hyogeunchild.security.jwt.JwtAuthFilter;
import com.sparta.team_1_hyogeunchild.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(prePostEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 가장 먼저 시큐리티를 사용하기 위해선 선언해준다.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용 및 resources 접근 허용 설정
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
//        http.authorizeHttpRequests()//요청에 대한 권한을 지정할 수 있다.
                //requestMatchers는 어느 주소범위로 할것인지 의미하며
                //.permitAll()는 시큐리티를 적용하지 않고 모두 통과시키겠다는의미이다.
                //   /api/post/get은 게시글과 댓글 전체조회일때 /api/post/get/**은 선택조회일때
                //   /api/post/**/comment 댓글을 조회할때
                //   /api/user/** 유저 생성이나 회원가입일때
                // 넣어주지 않으면 조회할때 로그인을 해서 토큰을 넣어줘야한다.
                // 그냥 로그인한 아무토큰만 넣어주면된다.. 조회할때는
                // 검사로직을 우리가 넣어주지 않았으니까 그냥 시큐리티 부분에서 막히는거다.
                // 회원가입에는 무조건 통과되게 해줘야한다.
                // 회원가입해야 로그인을 할수있는데 회원가입하려면 로그인하라는게 말이되겠는가?
                // 그리고.. 로그인도 로그인란으로 이동하게 해줘야하는데..
                // 잘못하면 두번인증한다. 로그인창으로 바로 이동하게 해주자.
                // http에서 해본게 아니라서 이걸 회원가입만 열어야할지 로그인만 열어야할지 둘다열어야하는지
                // http에서 해본사람만 알것이다.
                .antMatchers("/users/**").permitAll()
                .antMatchers("/h2-console").permitAll()
//                .antMatchers("/users/seller").hasAnyAuthority("ROLE_SELLER", "ROLE_ADMIN")
                .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/api/products/**").hasAnyAuthority("ROLE_SELLER")
//                .requestMatchers("/api/post/get/**/comment").permitAll()
                .anyRequest().authenticated()//인증이 되어야 한다는 이야기이다.
                //.anonymous() : 인증되지 않은 사용자도 접근할 수 있다.
                // JWT 인증/인가를 사용하기 위한 설정
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
//                .formLogin().failureHandler();

                http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandlerImpl());

//        http.formLogin().loginPage("/api/user/login-page").permitAll();
        // 이 부분에서 login 관련 문제 발생
        // jwt 로그인 방식에서는 세션 로그인 방식을 막아줘야 한다.
//        http.exceptionHandling().accessDeniedPage("/api/user/forbidden");

        return http.build();
    }
}