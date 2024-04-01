package com.example.be2ndproject.shopping_mall.dto.ask;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SearchAskResponseDTO {
    private Integer qnaId;
    private String title;
    private String author;
    private String createdAt;
}
