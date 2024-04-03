package com.example.be2ndproject.shopping_mall.dto.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAnswerDTO {
    private Integer spaceId;
    private Integer qnaId;
    private String content;
}
