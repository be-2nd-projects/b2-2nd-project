package com.example.be2ndproject.shopping_mall.dto.answer;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SearchAnswerResponseDTO {
    private Integer qnaId;
    private Integer answerId;
    private String title;
    private String content;
    private String author;
    private String createdAt;
}
