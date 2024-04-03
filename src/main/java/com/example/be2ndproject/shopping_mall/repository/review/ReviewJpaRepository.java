package com.example.be2ndproject.shopping_mall.repository.review;

import com.example.be2ndproject.shopping_mall.repository.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
public interface ReviewJpaRepository extends JpaRepository<Review,Integer> {


    void deleteByMember(Optional<Member> member);

    List<Review> findBySpaceSpaceId(Integer spaceId);

}
