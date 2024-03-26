package com.example.be2ndproject.shopping_mall.repository.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberJpaRepository extends JpaRepository<Members,Integer> {

    Members findByname(String membername);
}
