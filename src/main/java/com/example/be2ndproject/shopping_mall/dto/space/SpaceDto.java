package com.example.be2ndproject.shopping_mall.dto.space;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceDto {
    private Integer spaceId;
    private Integer userId;
    private String spaceName;
    private String spaceAddress;
    private Integer price;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String area;
    private String category;
    private Integer capacityNum;
    private String spaceStatus;
    private Float score;
    private List<String> imageUrls; // 이미지 URL 리스트
}
