package com.example.be2ndproject.shopping_mall.service;


import com.example.be2ndproject.shopping_mall.config.JWT.JwtTokenProvider;
import com.example.be2ndproject.shopping_mall.dto.auth.Login;
import com.example.be2ndproject.shopping_mall.dto.auth.SignUp;
import com.example.be2ndproject.shopping_mall.repository.member.MemberJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import com.example.be2ndproject.shopping_mall.repository.space.SpaceJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.reservation.ReservationJpaRepository;

import com.example.be2ndproject.shopping_mall.repository.review.ReviewJpaRepository;

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
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberJpaRepository memberJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SpaceJpaRepository spaceJpaRepository;
    private final ReservationJpaRepository reservationJpaRepository;
    private final ReviewJpaRepository reviewJpaRepository;


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
            log.info("error 비밀번호는 영문자 숫자 특수문자 조합하여 8글자에서 20글자 미만으로 설정해주세요");
            return "비밀번호는 영문자, 숫자, 특수문자를 조합하여 8글자에서 20글자 사이로 설정해주세요."; // 비밀번호 생성 규칙에 맞지 않으면 회원가입 실패
        }

        // 이메일 중복 확인 기능 추가
        if (isEmailAlreadyRegistered(email)) {
            log.info("error 이미 등록된 이메일입니다. 다른 이메일을 사용해주세요.");
            return "이미 등록된 이메일입니다. 다른 이메일을 사용해주세요.";// 이미 등록된 이메일이면 회원가입 실패
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        final String finalRoles;
        if (businessNumber != null) {
            finalRoles = "USER_HOST"; // businessNumber가 존재하면 역할을 USER_HOST로 변경
        } else {
            finalRoles = roles;
        }

        // 회원 가입 시간 설정 (현재 시간)
        LocalDateTime createAt = LocalDateTime.now();

        //  유저가 있으면 ID 만 등록 아니면 유저도 만들기
        Member memberFound = memberJpaRepository.findByEmail(email).orElseGet(() ->
                memberJpaRepository.save(Member.builder()
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
                        .createdAt(createAt) // 생성 시간 설정
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
        Optional<Member> existingMember = memberJpaRepository.findByEmail(email);
        return existingMember.isPresent();
    }

    public String login(Login loginRequest) throws Exception {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

            // 인증 시도
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);



        // JWT 토큰 생성
        return jwtTokenProvider.createToken(email);
    }


    @Transactional(transactionManager = "tmJpa")
    public void deleteUserByEmail(String userEmail) {
        // 이메일을 기반으로 회원 정보를 조회하여 삭제
        Optional<Member> member = memberJpaRepository.findByEmail(userEmail);
        if (member.isPresent()) {
            // 회원 정보가 존재하면 삭제
            Member existingMember = member.get();
            spaceJpaRepository.deleteByMember(Optional.of(existingMember));
            reservationJpaRepository.deleteByMember(Optional.of(existingMember));
            reviewJpaRepository.deleteByMember(Optional.of(existingMember));
            memberJpaRepository.delete(existingMember);
        } else {
            // 회원 정보가 존재하지 않는 경우 예외 처리 또는 적절한 응답 반환
            throw new RuntimeException("회원 정보를 찾을 수 없습니다.");
        }
    }


    public String getProfileImageUrl(String email) {
        // 이메일을 기반으로 회원 정보 조회
        Optional<Member> memberOptional = memberJpaRepository.findByEmail(email);

        // 회원 정보가 존재하는지 확인
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            // 회원의 프로필 이미지 URL 반환
            return member.getProfileImageUrl();
        } else {
            // 회원 정보가 존재하지 않는 경우
            return "회원가입 시 이미지 파일을 저장하지 않았습니다.";
        }
    }
}

