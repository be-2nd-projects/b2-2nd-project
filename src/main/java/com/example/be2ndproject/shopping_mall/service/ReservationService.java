package com.example.be2ndproject.shopping_mall.service;

import com.example.be2ndproject.shopping_mall.dto.ReservationDto;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import com.example.be2ndproject.shopping_mall.repository.member.MemberJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.reservation.Reservation;
import com.example.be2ndproject.shopping_mall.repository.reservation.ReservationJpaRepository;
import com.example.be2ndproject.shopping_mall.repository.space.Space;
import com.example.be2ndproject.shopping_mall.repository.space.SpaceJpaRepository;
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
    public Reservation createReservation(ReservationDto reservationDto) {
        Member member = memberJpaRepository.findById(reservationDto.getUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        Space space = spaceJpaRepository.findById(reservationDto.getSpaceId())
                .orElseThrow(() -> new RuntimeException("공간을 찾을 수 없습니다."));

        Reservation reservation = new Reservation();
        reservation.setMember(member);
        reservation.setSpace(space);
        reservation.setStartTime(LocalDateTime.of(reservationDto.getReservationDate(), reservationDto.getStartTime()));
        reservation.setEndTime(LocalDateTime.of(reservationDto.getReservationDate(), reservationDto.getEndTime()));
        reservation.setUserNum(reservationDto.getUserNum());
        reservation.setReservationStatus("PENDING"); // 예약 상태는 기본적으로 "대기중"으로 설정합니다.
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setUpdatedAt(LocalDateTime.now());

        // 예약 정보를 저장하고 반환합니다.
        return reservationJpaRepository.save(reservation);
    }
}
