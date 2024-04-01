package com.example.be2ndproject.shopping_mall.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {
    private Integer answerId;
    private String content;
    private String author;
    private Integer qnaId;
    private LocalDateTime createdAt;
}
