package com.example.be2ndproject.shopping_mall.service;

import com.example.be2ndproject.shopping_mall.dto.ReservationRequestDto;
import com.example.be2ndproject.shopping_mall.dto.ReservationResponseDto;
import com.example.be2ndproject.shopping_mall.repository.Member.Members;
import com.example.be2ndproject.shopping_mall.repository.Member.MemberJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.Reservation.ReservationJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.Reservation.Reservations;
import com.example.be2ndproject.shopping_mall.repository.Space.Spaces;
import com.example.be2ndproject.shopping_mall.repository.Space.SpaceJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReservationService {

    MemberJpaRepository memberJpaRepository;
    ReservationJpaRepository reservationJpaRepository;
    SpaceJpaRepository spaceJpaRepository;

    @Autowired
    public ReservationService(MemberJpaRepository memberJpaRepository, ReservationJpaRepository reservationJpaRepository, SpaceJpaRepository spaceJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
        this.reservationJpaRepository = reservationJpaRepository;
        this.spaceJpaRepository = spaceJpaRepository;
    }

    @Transactional
    public ReservationResponseDto createReservation(ReservationRequestDto reservationRequestDto) {
        Members user = memberJpaRepository.findById(reservationRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        Spaces space = spaceJpaRepository.findById(reservationRequestDto.getSpaceId())
                .orElseThrow(() -> new RuntimeException("공간을 찾을 수 없습니다."));

        Reservations reservation = new Reservations();
        reservation.setUser(user);
        reservation.setSpace(space);
        reservation.setStartTime(reservationRequestDto.getStartTime());
        reservation.setEndTime(reservationRequestDto.getEndTime());
        reservation.setUserNum(reservationRequestDto.getUserNum());
        reservation.setReservationDate(reservationRequestDto.getReservationDate());
        reservation.setReservationStatus("예약확정 대기중"); // 예약 상태는 기본적으로 "대기중"으로 설정합니다.
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setUpdatedAt(LocalDateTime.now());

        // 예약 정보를 저장하고 반환합니다.
        Reservations savedReservation = reservationJpaRepository.save(reservation);

        return new ReservationResponseDto(
                savedReservation.getReservationNum(),
                savedReservation.getUser().getUserId(),
                savedReservation.getSpace().getSpaceId(),
                savedReservation.getReservationStatus(),
                savedReservation.getReservationDate(),
                savedReservation.getStartTime(),
                savedReservation.getEndTime(),
                savedReservation.getUserNum()
        );
    }
}
