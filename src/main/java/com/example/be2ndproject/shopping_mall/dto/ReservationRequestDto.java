package com.example.be2ndproject.shopping_mall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDto {
    private int reservationNum;
    private int userId;
    private int spaceId;
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int userNum;
}