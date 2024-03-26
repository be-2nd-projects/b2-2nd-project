package com.example.be2ndproject.shopping_mall.repository.Asks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsksJpaRepository extends JpaRepository<Asks,Integer> {
}
