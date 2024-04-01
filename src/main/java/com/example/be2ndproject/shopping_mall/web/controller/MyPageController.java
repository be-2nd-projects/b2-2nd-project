package com.example.be2ndproject.shopping_mall.web.controller;

import com.example.be2ndproject.shopping_mall.config.auth.PrincipalDetails;
import com.example.be2ndproject.shopping_mall.service.MyPageService;
import com.example.be2ndproject.shopping_mall.web.dto.MyInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/account/")
public class MyPageController {

    private final MyPageService myPageService;

    // 내 정보 조회
    @GetMapping("/my-page")
    public MyInfoResponse findMyInfo (@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return myPageService.findMyInfo(principalDetails);
    }

}
