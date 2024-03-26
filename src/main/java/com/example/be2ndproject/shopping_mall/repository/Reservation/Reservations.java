package com.example.be2ndproject.shopping_mall.repository.Reservation;

import com.example.be2ndproject.shopping_mall.repository.Member.Members;
import com.example.be2ndproject.shopping_mall.repository.Space.Spaces;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Reservations")
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_num")
    private Long reservationNum;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Members user;

    @ManyToOne
    @JoinColumn(name = "space_id", referencedColumnName = "space_id")
    private Spaces space;

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

}