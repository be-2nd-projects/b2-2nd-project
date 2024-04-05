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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api")
public class LoginController {

    private final LoginService loginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomLogoutHandler customLogoutHandler;




    @Operation(summary = "회원가입")
    @PostMapping(value = "/sign")
    public ResponseEntity<?> signUp(@RequestBody SignUp signUpRequest) {

        String  signUpMessage  = loginService.signUp(signUpRequest);

        if (signUpMessage.equals("회원가입이 완료되었습니다.")) {
            return ResponseEntity.ok().body(signUpMessage);
        } else {
            return ResponseEntity.badRequest().body(signUpMessage);
        }
    }

    @Operation(summary = "로그인")
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody Login loginRequest, HttpServletResponse httpServletResponse) {

        // 로그인 시도
        String userEmail = loginService.login(loginRequest);

        // 토큰 생성 및 설정
        String token = jwtTokenProvider.createToken(userEmail);
        log.info("jwt 토큰 :"+token);
        httpServletResponse.setHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);

        // 로그인 성공 응답 반환
        return ResponseEntity.ok(Collections.singletonMap("message", "로그인이 성공적으로 완료되었습니다."));
    }

    @Operation(summary = "로그아웃")
    @PostMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // 현재 사용자 로그아웃 처리
        customLogoutHandler.logout(request, response, null);
        return "로그아웃 되었습니다.";
    }

//    @Operation(summary = "회원 탈퇴")
//    @DeleteMapping(value="/resign")
//    public ResponseEntity<String> resign(@RequestParam Integer userId) {
//        loginService.deleteUser(userId);
//        return ResponseEntity.ok("사용자가 성공적으로 탈퇴되었습니다.");
//    }



}
