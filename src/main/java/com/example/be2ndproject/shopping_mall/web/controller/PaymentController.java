package com.example.be2ndproject.shopping_mall.web.controller;

import com.example.be2ndproject.shopping_mall.dto.PaymentRequestDto;
import com.example.be2ndproject.shopping_mall.dto.PaymentResponseDto;
import com.example.be2ndproject.shopping_mall.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/payments")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDto> makePayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        PaymentResponseDto responseDto = paymentService.processPayment(paymentRequestDto);
        return ResponseEntity.ok(responseDto);
    }

}
