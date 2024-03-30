package com.example.be2ndproject.shopping_mall.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Integer userId;
    private Integer spaceId;
    private String title;
    private String content;
    private Float score;
}
