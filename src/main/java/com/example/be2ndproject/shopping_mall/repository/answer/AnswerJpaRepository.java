package com.example.be2ndproject.shopping_mall.repository.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerJpaRepository extends JpaRepository<Answer,Integer> {
}
