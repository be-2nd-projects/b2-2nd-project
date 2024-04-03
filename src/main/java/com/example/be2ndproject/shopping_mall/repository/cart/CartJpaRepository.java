package com.example.be2ndproject.shopping_mall.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartJpaRepository extends JpaRepository<Cart,Integer> {
    List<Cart> findAllByMember_UserId(Integer userId);
}
