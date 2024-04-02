package com.example.be2ndproject.shopping_mall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {
    private Integer totalPrice;
    private String paymentMethod;
    private String cardNumber;
    private Long reservationNumber;
    private Long accountId;
}
