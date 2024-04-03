package com.example.be2ndproject.shopping_mall.service;

import com.example.be2ndproject.shopping_mall.config.auth.PrincipalDetails;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import com.example.be2ndproject.shopping_mall.repository.member.MemberJpaRepository;
import com.example.be2ndproject.shopping_mall.service.exceptions.NotFoundException;
import com.example.be2ndproject.shopping_mall.web.dto.MyInfoRequest;
import com.example.be2ndproject.shopping_mall.web.dto.MyInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberJpaRepository memberJpaRepository;

    // 조회
    public MyInfoResponse findMyInfo(PrincipalDetails principalDetails) {
        Member member = memberJpaRepository.findById(principalDetails.getMember().getUserId())
                .orElseThrow( () -> new NotFoundException("등록된 회원이 아닙니다.", principalDetails.getMember().getUserId()));

        return MyInfoResponse.builder()
                .name(member.getName())
                .email(member.getEmail())
                .phoneNum(member.getPhoneNumber())
                .birthDate(member.getBirthDate())
                .createdAt(member.getCreatedAt())
                .userStatus(member.getUserStatus())
                .profileImgUrl(member.getProfileImageUrl())
                .businessNum(member.getBusinessNumber())
                .build();
    }

    public String updateMyInfo(PrincipalDetails principalDetails, MyInfoRequest myInfoRequest) {
        Member member = memberJpaRepository.findById(principalDetails.getMember().getUserId())
                .orElseThrow( () -> new NotFoundException("등록된 회원이 아닙니다.", principalDetails.getMember().getUserId()));

        // null 체크 후 수정
        if (myInfoRequest.getName() != null){
            member.setName(myInfoRequest.getName());
        }
        if (myInfoRequest.getEmail() != null){
            member.setEmail(myInfoRequest.getEmail());
        }
        if (myInfoRequest.getPassword() != null){
            member.setPassword(myInfoRequest.getPassword());
        }
        if (myInfoRequest.getPassword() != null){
            member.setPhoneNumber(myInfoRequest.getPhoneNum());
        }
        if (myInfoRequest.getProfileImgUrl() != null){
            member.setProfileImageUrl(myInfoRequest.getProfileImgUrl());
        }
        if (myInfoRequest.getBusinessNum() != null){
            member.setBusinessNumber(myInfoRequest.getBusinessNum());
        }

        memberJpaRepository.save(member);

        return "성공적으로 수정이 되었습니다.";



    }
}
