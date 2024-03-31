package com.example.be2ndproject.shopping_mall.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartDto {
    private Integer cartId;
    private Integer userId;
    private Integer spaceId;
    private String spaceName;
}
