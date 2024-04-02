package com.example.be2ndproject.shopping_mall.web.controller;

import com.example.be2ndproject.shopping_mall.config.auth.PrincipalDetails;
import com.example.be2ndproject.shopping_mall.dto.CartDto;
import com.example.be2ndproject.shopping_mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 장바구니 항목 추가
    @PostMapping("/add")
    public ResponseEntity<CartDto> addToCart(@RequestBody CartDto cartDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        cartDto.setUserId(principalDetails.getMember().getUserId());
        CartDto newCart = cartService.addToCart(cartDto);
        return ResponseEntity.ok(newCart);
    }

    // 장바구니 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartDto>> getCartItemsByUserId(@PathVariable Integer userId) {
        List<CartDto> cartItems = cartService.getCartItemsByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }

    // 장바구니 항목 삭제
    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Integer cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.ok().build();
    }
}
