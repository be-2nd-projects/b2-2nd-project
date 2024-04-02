package com.example.be2ndproject.shopping_mall.config.oauth;

import com.example.be2ndproject.shopping_mall.config.auth.PrincipalDetails;
import com.example.be2ndproject.shopping_mall.config.oauth.provider.GoogleUserInfo;
import com.example.be2ndproject.shopping_mall.config.oauth.provider.KakaoUserInfo;
import com.example.be2ndproject.shopping_mall.config.oauth.provider.NaverUserInfo;
import com.example.be2ndproject.shopping_mall.config.oauth.provider.OAuth2UserInfo;
import com.example.be2ndproject.shopping_mall.config.security.PasswordEncoderConfig;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import com.example.be2ndproject.shopping_mall.repository.member.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final MemberJpaRepository memberJpaRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("getClientRegistration:" + userRequest.getClientRegistration()); // Registration로 어떤 OAuth로 로그인 했는지 확인가능.
        log.info("getAccessToken:" + userRequest.getAccessToken().getTokenValue());


        OAuth2User oauth2User = super.loadUser(userRequest);
        // 구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인을 완료 -> code를 리턴(OAuth-Client라이브러리) ->AccessToken 요청
        // userRequest 정보 -> 회원 프로필 받아야함 (loadUser함수호출) -> (구글로부터)회원 프로필 받아준다
        log.info("getAttributes:" + oauth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            log.info("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            log.info("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map) oauth2User.getAttributes().get("response")); //{resultcode=00, message=success, response={id=--, email=--, name=--}}
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            log.info("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo((Map) oauth2User.getAttributes());
        } else {
            log.info("저희는 구글과 페이스북 ,네이버만 지원합니다.");
        }

        String provider = oAuth2UserInfo.getProvider(); // google , facebook
        String providerId = oAuth2UserInfo.getProviderId(); // "sub" 는 구글 적어줘야함
        String membername = provider + "_" + providerId; // google_109742856182916427686
        String password = passwordEncoderConfig.passwordEncoder().encode("겟인데어");
        String email = oAuth2UserInfo.getEmail();
        String roles = "ROLE_GUEST";

        Member memberEntity = memberJpaRepository.findByname(membername);


        if (memberEntity == null) {
            log.info("OAuth 로그인이 최초입니다.");
            memberEntity = Member.builder()
                    .name(membername)
                    .password(password)
                    .email(email)
                    .roles(roles)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            memberJpaRepository.save(memberEntity); // 회원가입시키는것

        } else {
            log.info("로그인을 이미 한적이 있습니다 자동 회원가입이 되어 있습니다.");
        }
        return new PrincipalDetails(memberEntity, oauth2User.getAttributes());

    }
}

