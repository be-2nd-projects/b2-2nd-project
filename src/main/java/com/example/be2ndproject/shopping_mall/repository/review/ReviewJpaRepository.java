package com.example.be2ndproject.shopping_mall.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewJpaRepository extends JpaRepository<Review,Integer> {

    List<Review> findBySpaceSpaceId(Integer spaceId);

}
