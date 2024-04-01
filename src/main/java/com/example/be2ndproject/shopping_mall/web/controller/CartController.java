package com.example.be2ndproject.shopping_mall.web.controller;

import com.example.be2ndproject.shopping_mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 장바구니 항목 추가
    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(){
        return null;
    }

    // 장바구니 조회


    // 장바구니 항목 삭제
}
