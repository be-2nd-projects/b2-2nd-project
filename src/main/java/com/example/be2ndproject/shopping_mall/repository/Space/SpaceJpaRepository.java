package com.example.be2ndproject.shopping_mall.repository.Space;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.be2ndproject.shopping_mall.repository.Member.Members;

import java.util.List;


@Repository
public interface SpaceJpaRepository extends JpaRepository<Spaces,Integer> {
    List<Spaces> findByUser(Members user_id);
}
