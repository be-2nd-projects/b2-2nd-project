package com.example.be2ndproject.shopping_mall.repository.paymentHistory;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PaymentHistory")
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Payment_history_id")
    private Integer paymentHistoryId;

    @Column(name = "payment_id", nullable = false)
    private Integer paymentId;

    @Column(name = "payment_completed", nullable = false)
    private boolean paymentCompleted = true;


}