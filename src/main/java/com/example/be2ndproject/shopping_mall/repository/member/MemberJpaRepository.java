package com.example.be2ndproject.shopping_mall.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member,Integer> {

    Optional<Member> findByEmail(String email);
}
