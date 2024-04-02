package com.example.be2ndproject.shopping_mall.web.controller;

import com.example.be2ndproject.shopping_mall.config.JWT.JwtProperties;
import com.example.be2ndproject.shopping_mall.config.JWT.JwtTokenProvider;
import com.example.be2ndproject.shopping_mall.config.auth.PrincipalDetails;
import com.example.be2ndproject.shopping_mall.config.oauth.PrincipalOauth2UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/v1/api")
public class OAuth2LoginController {

    private final PrincipalOauth2UserService principalOauth2UserService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "소셜 로그인")
    @PostMapping(value = "/social-login")
    public ResponseEntity<?> socialLogin(@RequestParam("provider") String provider, @RequestParam("code") String code, HttpServletResponse httpServletResponse) {
        try {
            // OAuth2 토큰 요청
            OAuth2AccessToken accessToken = requestAccessToken(provider, code);
            // OAuth2 사용자 정보 요청
            OAuth2User oauth2User = requestUserInfo(provider, accessToken);

            // PrincipalDetailsService를 통해 사용자 정보 저장 및 JWT 토큰 생성
            PrincipalDetails userDetails = (PrincipalDetails) principalOauth2UserService.loadUser((OAuth2UserRequest) oauth2User);

            // JWT 토큰 생성
            String token = jwtTokenProvider.createToken(userDetails.getUsername());

            // 응답 데이터 구성
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("token", token);
            responseBody.put("userDetails", userDetails);

            // HTTP 헤더에 JWT 토큰 추가
            httpServletResponse.setHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);

            // 로그인 성공 응답 반환
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            // 예외 발생 시 에러 응답 반환
            log.error("소셜 로그인 처리 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "소셜 로그인 처리 중 오류 발생"));
        }
    }

    private OAuth2AccessToken requestAccessToken(String provider, String code) {

        return null;
    }

    private OAuth2User requestUserInfo(String provider, OAuth2AccessToken accessToken) {
        return null;

    }
}
