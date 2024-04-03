package com.example.be2ndproject.shopping_mall.web.controller;

import com.example.be2ndproject.shopping_mall.config.JWT.JwtProperties;
import com.example.be2ndproject.shopping_mall.config.JWT.JwtTokenProvider;
import com.example.be2ndproject.shopping_mall.config.security.CustomLogoutHandler;
import com.example.be2ndproject.shopping_mall.dto.auth.Login;
import com.example.be2ndproject.shopping_mall.dto.auth.SignUp;
import com.example.be2ndproject.shopping_mall.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api")
public class LoginController {

    private final LoginService loginService;
    private final CustomLogoutHandler customLogoutHandler;


    @Operation(summary = "회원가입")
    @PostMapping(value = "/sign")
    public ResponseEntity<?> signUp(@RequestBody SignUp signUpRequest) {
        Map<String, Object> response = new HashMap<>();

        String signUpMessage = loginService.signUp(signUpRequest);

        if (signUpMessage.equals("회원가입이 완료되었습니다.")) {
            String message = signUpRequest.getName() + " 님이 " + signUpMessage; // 메시지 조합
            response.put("message", message);
            log.info(signUpRequest.getName() + "님이 회원가입이 완료되었습니다.");
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", signUpMessage));
        }
    }

    @Operation(summary = "로그인")
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody Login loginRequest, HttpServletResponse httpServletResponse) {
        try {
            // 로그인 시도 및 JWT 토큰 생성
            String token = loginService.login(loginRequest);

            // 로그인한 사용자의 프로필 이미지 URL 가져오기
            String profileImageUrl = loginService.getProfileImageUrl(loginRequest.getEmail());

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "로그인이 성공적으로 완료되었습니다.");
            responseBody.put("profileImageUrl", profileImageUrl);

            // 토큰을 HTTP 헤더에 추가
            httpServletResponse.setHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);
            log.info("jwt 토큰 :" + token);
            log.info("로그인이 성공적으로 완료되었습니다.");

            // 로그인 성공 응답 반환
            return ResponseEntity.ok(responseBody);
        } catch (BadCredentialsException e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "로그인 처리 중 문제가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    @Operation(summary = "로그아웃")
    @PostMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // 현재 사용자 로그아웃 처리
        customLogoutHandler.logout(request, response, null);
        log.info("로그아웃 되었습니다.");

    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping(value = "/resign")
    public ResponseEntity<Map<String, String>> resign() {
        // 현재 인증된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // 현재 사용자의 이메일을 얻어옴
            String userEmail = authentication.getName();
            // 이메일을 기반으로 회원 정보를 조회하여 삭제
            loginService.deleteUserByEmail(userEmail);
            // JSON 형식의 응답 생성
            Map<String, String> response = new HashMap<>();
            response.put("message", "사용자가 성공적으로 탈퇴되었습니다.");
            log.info("사용자가 성공적으로 탈퇴되었습니다.");
            return ResponseEntity.ok(response);
        } else {
            // 사용자가 인증되지 않은 경우 처리
            log.info("인증되지 않은 사용자입니다.");
            throw new RuntimeException("인증되지 않은 사용자입니다.");

        }


    }
}
