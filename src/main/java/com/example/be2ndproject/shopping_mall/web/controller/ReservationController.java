package com.example.be2ndproject.shopping_mall.web.controller;

import com.example.be2ndproject.shopping_mall.dto.ReservationDto;
import com.example.be2ndproject.shopping_mall.repository.reservation.Reservation;
import com.example.be2ndproject.shopping_mall.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDto reservationDto) {
        Reservation createdReservation = reservationService.createReservation(reservationDto);
        return ResponseEntity.ok(createdReservation);
    }
}
