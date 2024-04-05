package com.example.be2ndproject.shopping_mall.repository.reservation;

import com.example.be2ndproject.shopping_mall.repository.member.Member;
import com.example.be2ndproject.shopping_mall.repository.space.Space;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
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
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

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

    @Column(name = "reservation_date")
    private LocalDate reservationDate;
}