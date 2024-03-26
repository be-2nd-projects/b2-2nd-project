package com.example.be2ndproject.shopping_mall.repository.PaymentHistory;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymenHitoryJpaRepository extends JpaRepository<PaymentHistory,Integer> {
}
