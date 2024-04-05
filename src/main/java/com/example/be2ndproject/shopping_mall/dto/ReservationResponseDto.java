package com.example.be2ndproject.shopping_mall.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDto {
    private Integer reservationNum;
    private Integer userId;
    private Integer spaceId;
    private String reservationStatus;
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int userNum;
}