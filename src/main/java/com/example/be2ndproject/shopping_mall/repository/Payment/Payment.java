package com.example.be2ndproject.shopping_mall.repository.Payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "payment", nullable = false, length = 45)
    private String paymentMethod;

    @Column(name = "card_num", nullable = false, length = 45)
    private String cardNumber;

    @Column(name = "reservation_num", nullable = false)
    private Long reservationNumber;

    @Column(name = "payment_time", nullable = false)
    private LocalDateTime paymentTime;

    @Column(name = "account_id", nullable = false)
    private Long accountId;


}
