package com.example.be2ndproject.shopping_mall.service;

import com.example.be2ndproject.shopping_mall.dto.PaymentRequestDto;
import com.example.be2ndproject.shopping_mall.dto.PaymentResponseDto;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import com.example.be2ndproject.shopping_mall.repository.member.MemberJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.payment.Payment;
import com.example.be2ndproject.shopping_mall.repository.payment.PaymentJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private PaymentJpaRepository paymentJpaRepository;
    private MemberJpaRepository memberJpaRepository;

    public PaymentService(PaymentJpaRepository paymentJpaRepository, MemberJpaRepository memberJpaRepository) {
        this.paymentJpaRepository = paymentJpaRepository;
        this.memberJpaRepository = memberJpaRepository;
    }

    private Member getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        return memberJpaRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. : " + currentEmail));
    }

    public PaymentResponseDto processPayment(PaymentRequestDto paymentRequestDto) {
        Member currentUser = getCurrentUser();

        Payment payment = new Payment();

        payment.setTotalPrice(paymentRequestDto.getTotalPrice());
        payment.setPaymentMethod(paymentRequestDto.getPaymentMethod());
        payment.setCardNumber(paymentRequestDto.getCardNumber());
        payment.setReservationNumber(paymentRequestDto.getReservationNumber());
        payment.setAccountId(paymentRequestDto.getAccountId());
        payment.setPaymentTime(LocalDateTime.now());

        paymentJpaRepository.save(payment);

        return new PaymentResponseDto("success","결제가 성공적으로 처리되었습니다.");
    }
}
