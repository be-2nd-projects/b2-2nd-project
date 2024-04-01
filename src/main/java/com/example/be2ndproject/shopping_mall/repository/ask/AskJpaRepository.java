package com.example.be2ndproject.shopping_mall.repository.ask;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AskJpaRepository extends JpaRepository<Ask,Integer> {
}
