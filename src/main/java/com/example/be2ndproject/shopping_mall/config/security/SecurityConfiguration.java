package com.example.be2ndproject.shopping_mall.config.security;

import com.example.be2ndproject.shopping_mall.repository.Member.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CorsConfig corsConfig;
    private final MemberJpaRepository memberJpaRepository;


    // SecurityFilterChain 빈을 등록하여 HTTP 요청에 대한 보안 필터 체인을 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()); // CSRF 보호 기능 비활성화
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // session 사용 안함 (stateless)
                .and()
                .formLogin(f -> f.disable()) // 폼태그(html)를 사용해서 로그인 사용안함
                .httpBasic(h -> h.disable())
                // 클라이언트가 서버에 요청을 보낼 때 사용자 이름과 비밀번호를 인증하는 간단한 방법
                // 클라이언트가 요청을 보낼 때, 인증 정보는 요청 헤더에 Base64로 인코딩되어 포함. 서버는 이 정보를 확인하여 클라이언트의 요청을 처리하거나 거부.
                // 이 방법은 간단하고 쉽게 구현할 수 있지만, 보안 수준이 낮고 인증 정보가 평문으로 전송되기 때문에 보안에 취약함.
                // Http Basic 인증을 사용하지 않도록 Spring Security 구성을 설정
                .authorizeHttpRequests(authorize -> // 요청에 대한 접근 권한을 설정
                        authorize.requestMatchers("/togather/guest/**").hasAnyRole("GUEST", "HOST")
                                .requestMatchers("/togather/host/**").hasRole("HOST")
                                .requestMatchers("/togather/sign", "/togather/login", "/v2/api-docs",
                                        "/configuration/ui", "/swagger-resources/**", "/configuration/security",
                                        "/swagger-ui/**", "/webjars/**", "/swagger/**").permitAll() // 회원가입, 로그인 경로는 모두에게 허용 Swagger 문서 관련 경로는 모두에게 허용
                                .anyRequest().permitAll()) // 그 외 모든 요청은 인증을 필요로 함
                .logout(logout -> logout.logoutUrl("/togather/logout") // 로그아웃 설정 ,  로그아웃 처리 URL
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트 URL
                        .permitAll());
        
        // 기타 필요한 설정 추가
        http.with(new MyCustomDs(), myCustomDs -> myCustomDs.getClass());

        return http.build(); // HttpSecurity 설정을 기반으로 SecurityFilterChain 객체 생성 및 반환
    }

    public class MyCustomDs extends AbstractHttpConfigurer<MyCustomDs, HttpSecurity> { // custom Filter

        // HttpSecurity를 사용하여 웹 보안을 구성하는 메소드
        @Override
        public void configure(HttpSecurity http) throws Exception {

            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http.addFilter(corsConfig.corsFilter());  // @CrossOrigin(인증x) , 시큐리티 필터에 등록 인증(O)
//                    .addFilter(new JwtAuthenticationFilter(authenticationManager))  // AuthenticationManager를 Parameter로 넘겨줘야 함(로그인을 진행하는 데이터이기 때문)
//                    .addFilter(new JwtAuthorizationFilter(authenticationManager,memberJpaRepository));

        }
    }

}
