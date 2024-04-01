package com.example.be2ndproject.shopping_mall.service.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
    private String detailMessage;
    private Object request;

    public NotFoundException(String detailMessage, Object request) {
        this.detailMessage = detailMessage;
        this.request = request;
    }

}
