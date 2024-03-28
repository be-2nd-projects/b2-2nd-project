package com.example.be2ndproject.shopping_mall.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        // HTTP 요청이 JSON 형식인 경우에만 처리
        String contentType = request.getHeader("Content-Type");

        if (contentType != null && contentType.contains("application/json")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 인증 실패 상태 코드
            response.setContentType("application/json"); // JSON 형식으로 응답
            response.setCharacterEncoding("UTF-8"); // 문자 인코딩 설정

            // 오류 메시지를 JSON 형식으로 작성하여 응답
            PrintWriter writer = response.getWriter();
            writer.println("{ \"error\": \"이메일 또는 비밀번호가 올바르지 않습니다.\" }");
            writer.flush();

        } else {
            // JSON 형식이 아닌 경우에는 리다이렉트하여 로그인 페이지로 이동
            response.sendRedirect("/v1/api/login");
        }

        if (request.getRequestURI().equals("/v1/api/login")) {
            // 이미 로그인 페이지로 요청 중인 경우, 다시 리다이렉트할 필요가 없음
            return;
        }

        response.sendRedirect("/v1/api/login");
        // commence 메소드는 인증 과정에서 예외가 발생했을 때 실행
        // 사용자를 "/v1/api" 경로로 리다이렉트 이는 사용자가 인증되지 않았을 때 보여줄 페이지 또는 로그인 페이지로 이동시키는 용도로 사용
    }






}