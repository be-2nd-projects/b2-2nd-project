package com.example.be2ndproject.shopping_mall.repository.Reservation;

import com.example.be2ndproject.shopping_mall.repository.Member.Members;
import com.example.be2ndproject.shopping_mall.repository.Space.Spaces;
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
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_num")
    private Integer reservationNum;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Members user;

    @ManyToOne
    @JoinColumn(name = "space_id", referencedColumnName = "space_id")
    private Spaces space;

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

<<<<<<< Updated upstream:src/main/java/com/example/be2ndproject/shopping_mall/repository/Reservation/Reservations.java
=======
    @Column(name = "user_num")
    private int userNum;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;
>>>>>>> Stashed changes:src/main/java/com/example/be2ndproject/shopping_mall/repository/reservation/Reservation.java
}