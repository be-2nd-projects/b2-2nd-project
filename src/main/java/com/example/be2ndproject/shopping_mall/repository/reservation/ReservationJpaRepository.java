package com.example.be2ndproject.shopping_mall.repository.reservation;

import com.example.be2ndproject.shopping_mall.repository.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationJpaRepository extends JpaRepository<Reservation,Integer> {
    void deleteByMember(Optional<Member> member);
}
