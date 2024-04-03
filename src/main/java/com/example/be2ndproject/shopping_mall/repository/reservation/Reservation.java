package com.example.be2ndproject.shopping_mall.repository.reservation;

import com.example.be2ndproject.shopping_mall.repository.space.Space;
import com.example.be2ndproject.shopping_mall.repository.member.Member;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_num")
    private Integer reservationNum;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "space_id", referencedColumnName = "space_id")
    private Space space;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "reservation_status")
    private String reservationStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "user_num")
    private int userNum;

}