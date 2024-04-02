package com.example.be2ndproject.shopping_mall.dto.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAnswerDTO {
    private Integer qnaId;
    private Integer answerId;
    private String content;
}
