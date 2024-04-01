package com.example.be2ndproject.shopping_mall.web.controller;

import com.example.be2ndproject.shopping_mall.config.auth.PrincipalDetails;
import com.example.be2ndproject.shopping_mall.service.ReviewService;
import com.example.be2ndproject.shopping_mall.web.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping("/review")
    public String writeReview(@AuthenticationPrincipal PrincipalDetails principalDetails,
                              @RequestBody ReviewDto reviewDto) {
        return reviewService.writeReview(principalDetails, reviewDto);
    }

    // 전체 리뷰 조회
    @GetMapping("/find-review/all")
    public List<ReviewDto> findAllReview() {
        return reviewService.findAllReview();
    }

    // 리뷰 삭제
//    @DeleteMapping("/review/{reviewId}")
//    public ReviewDto deleteReview (@PathVariable Integer reviewId,
//                                   @AuthenticationPrincipal PrincipalDetails principalDetails) {
//        return reviewService.deleteReview(reviewId, principalDetails);
//    }



}
