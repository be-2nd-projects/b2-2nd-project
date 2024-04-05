package com.example.be2ndproject.shopping_mall.web.controller;


import com.example.be2ndproject.shopping_mall.dto.space.SpaceDto;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import com.example.be2ndproject.shopping_mall.repository.member.MemberJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.space.Space;
import com.example.be2ndproject.shopping_mall.repository.space.SpaceJpaRepository;
import com.example.be2ndproject.shopping_mall.service.SpaceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/space")
public class SpaceController {
    private final SpaceService spaceService;
    private final MemberJpaRepository memberJpaRepository;
    private final SpaceJpaRepository spaceJpaRepository;


    @Operation(summary = "공간 등록")
    @PostMapping("")
    public ResponseEntity<?> createSpace(@RequestBody SpaceDto spaceDto) {
        String message = spaceService.createSpace(spaceDto);
        return ResponseEntity.ok().body(message);
    }

    @Operation(summary = "모든 판매물품 조회")
    @GetMapping("")
    public ResponseEntity<?> getAllSpaces() {
        // 현재 사용자의 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 사용자의 이메일을 가져옴
        String currentPrincipalName = authentication.getName();

        // 이메일을 사용하여 해당 사용자를 조회
        Member currentUser = memberJpaRepository.findByEmail(currentPrincipalName)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일에 해당하는 사용자를 찾을 수 없습니다."));

        // 만약 사용자가 호스트 권한을 가지고 있다면 판매물품 조회를 허용
        if ("HOST".equals(currentUser.getRoles())) {
            List<Space> spaces = spaceService.getAllSpaces();
            return ResponseEntity.ok().body(spaces);
        } else {
            // 호스트 권한을 가지고 있지 않으면 권한이 없다는 메시지 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("호스트 권한이 없어서 판매물품을 조회할 수 없습니다.");
        }
    }
}

