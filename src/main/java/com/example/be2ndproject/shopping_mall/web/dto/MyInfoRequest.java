package com.example.be2ndproject.shopping_mall.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyInfoRequest {

    private String name;
    private String email;
    private String password;
    private String phoneNum;
    private String profileImgUrl;
    private Integer businessNum;
}
