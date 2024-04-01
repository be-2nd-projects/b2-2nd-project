package com.example.be2ndproject.shopping_mall.service;

import com.example.be2ndproject.shopping_mall.config.auth.PrincipalDetails;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import com.example.be2ndproject.shopping_mall.repository.member.MemberJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.review.Review;
import com.example.be2ndproject.shopping_mall.repository.review.ReviewJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.space.Space;
import com.example.be2ndproject.shopping_mall.repository.space.SpaceJpaRepository;
import com.example.be2ndproject.shopping_mall.service.exceptions.NotFoundException;
import com.example.be2ndproject.shopping_mall.web.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewJpaRepository reviewJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final SpaceJpaRepository spaceJpaRepository;
    public String writeReview(PrincipalDetails principalDetails, ReviewDto reviewDto) {
        Member member = memberJpaRepository.findById(principalDetails.getMember().getUserId())
                .orElseThrow( () -> new NotFoundException("사용자를 찾을 수 없습니다.", principalDetails.getMember().getUserId()));
        Space space = spaceJpaRepository.findById(reviewDto.getSpaceId())
                .orElseThrow( () -> new NotFoundException("등록되지 않은 공간입니다.", reviewDto.getSpaceId()));
        Review review = Review.builder()
                .member(member)
                .space(space)
                .title(reviewDto.getTitle())
                .content(reviewDto.getContent())
                .score(reviewDto.getScore())
                .build();

        reviewJpaRepository.save(review);

        updateSpaceRating(reviewDto.getSpaceId());

        return "리뷰가 성공적으로 등록되었습니다.";
    }

    private void updateSpaceRating(Integer spaceId) {
        // spaceId로 spaceEntity 조회
        Space space = spaceJpaRepository.findById(spaceId)
                .orElseThrow( () -> new NotFoundException("등록되지 않은 공간입니다.", spaceId));

        List<Review> reviewList = reviewJpaRepository.findBySpaceSpaceId(spaceId);
        float totalScore = 0;
        for (Review review : reviewList) {
            totalScore += review.getScore();
        }
        space.setScore(totalScore / reviewList.size());

        spaceJpaRepository.save(space);

    }

    // 전체 조회
    public List<ReviewDto> findAllReview() {
        List<Review> reviewList = reviewJpaRepository.findAll();

        return reviewList.stream()
                .map(review -> ReviewDto.builder()
                        .userId(review.getMember().getUserId())
                        .spaceId(review.getSpace().getSpaceId())
                        .title(review.getTitle())
                        .content(review.getContent())
                        .score(review.getScore())
                        .build())
                .collect(Collectors.toList());
    }

//    public ReviewDto deleteReview(Integer reviewId, PrincipalDetails principalDetails) {
//        Review review = reviewJpaRepository.findById(reviewId)
//                .orElseThrow( () -> new NotFoundException("게시물을 찾을 수 없습니다.", reviewId));
//
//        Member member = memberJpaRepository.findById(principalDetails.getMember().getUserId())
//                .orElseThrow( () -> new NotFoundException("유저를 찾을 수 없습니다.", principalDetails.getMember().getUserId()));
//
//
//
//    }
}
