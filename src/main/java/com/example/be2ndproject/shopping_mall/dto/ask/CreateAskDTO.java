package com.example.be2ndproject.shopping_mall.dto.ask;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAskDTO {
    private Integer spaceId;
    private String title;
    private String email;
    private String content;
}
