package com.example.be2ndproject.shopping_mall.service;

import com.example.be2ndproject.shopping_mall.dto.space.SpaceDto;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import com.example.be2ndproject.shopping_mall.repository.member.MemberJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.space.Space;
import com.example.be2ndproject.shopping_mall.repository.space.SpaceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.List;
@Service
@RequiredArgsConstructor
public class SpaceService {
    private final SpaceJpaRepository spaceJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    // 모든 판매물품 조회 메서드 추가
    public List<Space> getAllSpaces() {
        return spaceJpaRepository.findAll();
    }
    @Transactional
    public String createSpace(SpaceDto spaceDto) {
        // userId를 사용하여 Member 객체를 조회
        Member member = memberJpaRepository.findById(spaceDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 userId에 해당하는 Member를 찾을 수 없습니다."));

        // 호스트 권한인 경우에만 공간 등록
        if ("HOST".equals(member.getRoles())) {
            // Space 엔티티 생성
            Space space = Space.builder()
                    .member(member)
                    .spaceName(spaceDto.getSpaceName())
                    .spaceAddress(spaceDto.getSpaceAddress())
                    .price(spaceDto.getPrice())
                    .content(spaceDto.getContent())
                    .createdAt(LocalDateTime.now())
                    .area(spaceDto.getArea())
                    .category(spaceDto.getCategory())
                    .capacityNum(spaceDto.getCapacityNum())
                    .spaceStatus(spaceDto.getSpaceStatus())
                    .score(spaceDto.getScore())
                    .build();

            spaceJpaRepository.save(space);
            return "공간이 성공적으로 등록되었습니다.";
        } else {
            return "호스트 권한이 없어서 공간을 등록할 수 없습니다.";
        }

    }
}
