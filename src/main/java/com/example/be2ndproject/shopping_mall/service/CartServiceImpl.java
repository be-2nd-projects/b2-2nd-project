package com.example.be2ndproject.shopping_mall.service;

import com.example.be2ndproject.shopping_mall.dto.CartDto;
import com.example.be2ndproject.shopping_mall.repository.cart.Cart;
import com.example.be2ndproject.shopping_mall.repository.cart.CartJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import com.example.be2ndproject.shopping_mall.repository.member.MemberJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.space.Space;
import com.example.be2ndproject.shopping_mall.repository.space.SpaceJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartJpaRepository cartJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final SpaceJpaRepository spaceJpaRepository;

    @Override
    public CartDto addToCart(CartDto cartDto) {

        // 사용자랑 공간 조회
        Member user = memberJpaRepository.findById(cartDto.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾지 못했습니다. ID : " + cartDto.getUserId()));
        Space space = spaceJpaRepository.findById(cartDto.getSpaceId())
                .orElseThrow(() -> new RuntimeException("공간정보를 찾지 못했습니다. ID : " + cartDto.getSpaceId()));

        // 엔티티 생성 및 초기화
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setSpace(space);

        // 카트 저장
        Cart savedCart = cartJpaRepository.save(cart);

        // 새 Dto 생성하여 반환
        CartDto savedCartDto = new CartDto();
        savedCartDto.setCartId(savedCart.getCartId());
        savedCartDto.setUserId(user.getUserId());
        savedCartDto.setSpaceId(space.getSpaceId());

        return savedCartDto;
    }

    @Override
    public List<CartDto> getCartItemsByUserId(Integer userId) {

        // 사용자 Id에 해당하는 카트 아이템 전부 조회
        List<Cart> carts = cartJpaRepository.findAllByUser_UserId(userId);

        // 카트 엔티티 리스트를 CartDto 리스트로 변환(공간명만 가져오게 함. 필요시 set, get 추가)
        return carts.stream().map(cart -> {
            CartDto cartDto = new CartDto();
            cartDto.setSpaceName(cart.getSpace().getSpaceName());
            return cartDto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteCart(Integer cartId) {
        Cart cart = cartJpaRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("카트정보를 찾을 수 없습니다" + cartId));
        cartJpaRepository.delete(cart);
    }
}
