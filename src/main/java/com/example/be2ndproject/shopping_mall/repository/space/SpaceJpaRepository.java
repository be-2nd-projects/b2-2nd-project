package com.example.be2ndproject.shopping_mall.repository.space;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.be2ndproject.shopping_mall.repository.Member.Members;

import java.util.List;


@Repository
public interface SpaceJpaRepository extends JpaRepository<Space,Integer> {
    List<Space> findByUser(Members user_id);
}
