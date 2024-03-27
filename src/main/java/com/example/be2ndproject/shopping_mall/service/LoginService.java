package com.example.be2ndproject.shopping_mall.service;


import com.example.be2ndproject.shopping_mall.config.JWT.JwtTokenProvider;
import com.example.be2ndproject.shopping_mall.dto.auth.Login;
import com.example.be2ndproject.shopping_mall.dto.auth.SignUp;
import com.example.be2ndproject.shopping_mall.repository.Member.MemberJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.Member.Members;
import com.example.be2ndproject.shopping_mall.repository.Reservation.ReservationJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.Reservation.Reservations;
import com.example.be2ndproject.shopping_mall.repository.Review.ReviewJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.Space.SpaceJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberJpaRepository memberJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;



    @Transactional(transactionManager = "tmJpa")
    public String  signUp(SignUp signUpRequest) {
        String name = signUpRequest.getName();              // NOT NUL
        String email = signUpRequest.getEmail();            // NOT NUL
        String password = signUpRequest.getPassword();      // NOT NUL
        String phoneNumber = signUpRequest.getPhoneNumber(); // NOT NUL
        LocalDate birthDate = signUpRequest.getBirthDate();  // NOT NUL
        String profileImageUrl = signUpRequest.getProfileImageUrl();  // NULL 허용 사진
        Integer businessNumber = signUpRequest.getBusinessNumber();   // NULL 허용 사업자 번호
        String roles = signUpRequest.getRoles();                      // NOT NULL 기본 USER_GUEST
        String providerId = signUpRequest.getProviderId();            //  NULL 허용 소셜 로그인
        String provider = signUpRequest.getProvider();                //  NULL 허용 소셜 로그인

        // 비밀번호 생성 규칙 추가
        if (!isPasswordValid(password)) {
            log.info("비밀번호는 영문자 숫자 특수문자 조합하여 8글자에서 20글자 미만으로 설정해주세요");
            return "비밀번호는 영문자, 숫자, 특수문자를 조합하여 8글자에서 20글자 사이로 설정해주세요." ; // 비밀번호 생성 규칙에 맞지 않으면 회원가입 실패
        }

        // 이메일 중복 확인 기능 추가
        if (isEmailAlreadyRegistered(email)) {
            return "이미 등록된 이메일입니다. 다른 이메일을 사용해주세요."; // 이미 등록된 이메일이면 회원가입 실패
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        final String finalRoles;
        if (businessNumber != null) {
            finalRoles = "USER_HOST"; // businessNumber가 존재하면 역할을 USER_HOST로 변경
        } else {
            finalRoles = roles;
        }

        //  유저가 있으면 ID 만 등록 아니면 유저도 만들기
        Members membersFound = memberJpaRepository.findByEmail(email).orElseGet(() ->
                memberJpaRepository.save(Members.builder()
                        .name(name)
                        .email(email)
                        .password(encodedPassword) // 암호화된 비밀번호 저장
                        .phoneNumber(phoneNumber)
                        .birthDate(birthDate)
                        .profileImageUrl(profileImageUrl)
                        .roles(finalRoles)
                        .businessNumber(businessNumber)
                        .providerId(providerId)
                        .provider(provider)
                        .build()
                ));
        return "회원가입이 완료되었습니다.";
    }

    // 비밀번호 생성 규칙 검사 메서드
    private boolean isPasswordValid(String password) {
        // 정규 표현식을 사용하여 비밀번호가 영문자와 숫자의 조합으로 8자 이상 20자 이하인지 확인
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$";
        return password.matches(regex);
    }

    // 이메일 중복 확인 메서드
    private boolean isEmailAlreadyRegistered(String email) {
        // 이미 등록된 이메일인지 확인하는 로직을 작성하여 반환
        Optional<Members> existingMember = memberJpaRepository.findByEmail(email);
        return existingMember.isPresent();
    }

    public String login(Login loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // 인증 시도
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT 토큰 생성
        return jwtTokenProvider.createToken(email);

    }

    public String createToken(String email) {
        String token = jwtTokenProvider.createToken(email); // 토큰 생성 및 변수에 저장
        return token; // 생성된 토큰 반환
    }

//    @Transactional(transactionManager = "tmJpa")
//    public void deleteUser(Integer userId) {
//        memberJpaRepository.deleteById(userId);
//        // 사용자와 관련된 다른 테이블의 레코드 삭제 (예: 공간 정보, 예약 정보 등)
//        spaceJpaRepository.deleteByUserId(userId);
//        reservationJpaRepository.deleteByUserId(userId);
//        reviewJpaRepository.deleteByUserId(userId);
//    }
}

