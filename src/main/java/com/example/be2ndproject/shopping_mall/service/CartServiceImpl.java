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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartJpaRepository cartJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final SpaceJpaRepository spaceJpaRepository;

    private Member getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        return memberJpaRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. : " + currentEmail));
    }

    @Override
    public CartDto addToCart(CartDto cartDto) {

        Member currentUser = getCurrentUser();

        if (!currentUser.getUserId().equals(cartDto.getUserId())) {
            throw new IllegalStateException("사용자는 본인의 카트만 수정할 수 있습니다.");
        }

        // 사용자랑 공간 조회
        Space space = spaceJpaRepository.findById(cartDto.getSpaceId())
                .orElseThrow(() -> new RuntimeException("공간정보를 찾지 못했습니다. ID : " + cartDto.getSpaceId()));

        // 엔티티 생성 및 초기화
        Cart cart = new Cart();
        cart.setMember(currentUser);
        cart.setSpace(space);

        // 카트 저장
        Cart savedCart = cartJpaRepository.save(cart);

        // 새 Dto 생성하여 반환
        CartDto savedCartDto = new CartDto();
        savedCartDto.setCartId(savedCart.getCartId());
        savedCartDto.setUserId(currentUser.getUserId());
        savedCartDto.setSpaceId(space.getSpaceId());

        return savedCartDto;
    }

    @Override
    public List<CartDto> getCartItemsByUserId(Integer userId) {
        Member currentUser = getCurrentUser();

        // 요청된 userId와 현재 로그인한 사용자의 ID가 다른 경우 예외 처리
        if (!currentUser.getUserId().equals(userId)) {
            throw new IllegalStateException("사용자는 본인의 카트 정보만 조회할 수 있습니다.");
        }

        // 사용자 Id에 해당하는 카트 아이템 전부 조회
        List<Cart> carts = cartJpaRepository.findAllByMember_UserId(userId);

        // 카트 엔티티 리스트를 CartDto 리스트로 변환(공간명만 가져오게 함. 필요시 set, get 추가)
        return carts.stream().map(cart -> {
            CartDto cartDto = new CartDto();
            cartDto.setSpaceName(cart.getSpace().getSpaceName());
            return cartDto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteCart(Integer cartId) {
        Member currentUser = getCurrentUser();
        Cart cart = cartJpaRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("카트정보를 찾을 수 없습니다" + cartId));

        if (!cart.getMember().equals(currentUser)) {
            throw new IllegalStateException("사용자는 본인의 카트만 삭제할 수 있습니다.");
        }

        cartJpaRepository.delete(cart);
    }
}
