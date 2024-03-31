package com.example.be2ndproject.shopping_mall.repository.space;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceJpaRepository extends JpaRepository<Space,Integer> {

}
