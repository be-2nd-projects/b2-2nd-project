package com.example.be2ndproject.shopping_mall.service;

import com.example.be2ndproject.shopping_mall.dto.PaymentRequestDto;
import com.example.be2ndproject.shopping_mall.dto.PaymentResponseDto;
import com.example.be2ndproject.shopping_mall.repository.payment.Payment;
import com.example.be2ndproject.shopping_mall.repository.payment.PaymentJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private PaymentJpaRepository paymentJpaRepository;

    public PaymentService(PaymentJpaRepository paymentJpaRepository) {
        this.paymentJpaRepository = paymentJpaRepository;
    }

    public PaymentResponseDto processPayment(PaymentRequestDto paymentRequestDto) {
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
