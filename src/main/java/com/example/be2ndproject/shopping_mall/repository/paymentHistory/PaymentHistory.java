package com.example.be2ndproject.shopping_mall.repository.paymentHistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
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