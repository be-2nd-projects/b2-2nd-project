package com.example.be2ndproject.shopping_mall.repository.Space;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceJpaRepository extends JpaRepository<Spaces,Integer> {
}
