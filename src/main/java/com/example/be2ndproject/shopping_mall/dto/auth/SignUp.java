package com.example.be2ndproject.shopping_mall.dto.auth;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUp {
    private String name; // NOT NUL
    private String email; // NOT NUL
    private String password; // NOT NUL
    private String phoneNumber; // NOT NUL
    private LocalDate birthDate; // NOT NUL
    private LocalDateTime createAt; // Null 허용
    private String profileImageUrl; // NULL 허용 사진
    private Integer businessNumber; // NULL 허용 사업자 번호
    private String roles; // NOT NULL 기본 USER_GUEST
    private String providerId; //  NULL 허용 소셜 로그인
    private String provider; //  NULL 허용 소셜 로그인

}
