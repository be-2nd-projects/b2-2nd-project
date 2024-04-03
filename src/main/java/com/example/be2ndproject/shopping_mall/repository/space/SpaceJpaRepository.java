package com.example.be2ndproject.shopping_mall.repository.space;

import com.example.be2ndproject.shopping_mall.repository.member.Member;
import com.example.be2ndproject.shopping_mall.repository.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpaceJpaRepository extends JpaRepository<Space,Integer> {


    void deleteByMember(Optional<Member> member);

}
