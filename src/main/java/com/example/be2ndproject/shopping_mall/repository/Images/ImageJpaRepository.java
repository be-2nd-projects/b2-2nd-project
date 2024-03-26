package com.example.be2ndproject.shopping_mall.repository.Images;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageJpaRepository extends JpaRepository<Images,Integer> {
}
