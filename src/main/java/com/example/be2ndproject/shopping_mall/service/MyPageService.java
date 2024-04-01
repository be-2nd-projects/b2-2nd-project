package com.example.be2ndproject.shopping_mall.service;

import com.example.be2ndproject.shopping_mall.config.auth.PrincipalDetails;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import com.example.be2ndproject.shopping_mall.repository.member.MemberJpaRepository;
import com.example.be2ndproject.shopping_mall.service.exceptions.NotFoundException;
import com.example.be2ndproject.shopping_mall.web.dto.MyInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberJpaRepository memberJpaRepository;
    public MyInfoResponse findMyInfo(PrincipalDetails principalDetails) {
        Member member = memberJpaRepository.findById(principalDetails.getMember().getUserId())
                .orElseThrow( () -> new NotFoundException("등록된 회원이 아닙니다.", principalDetails.getMember().getUserId()));

    }
}
