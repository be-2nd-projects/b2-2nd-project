package com.example.be2ndproject.shopping_mall.service;

import com.example.be2ndproject.shopping_mall.dto.CartDto;

import java.util.List;

public interface CartService {
    CartDto addToCart(CartDto cartDto);

    List<CartDto> getCartItemsByUserId(Integer userId);

    void deleteCart(Integer cartId);
}
