package com.example.be2ndproject.shopping_mall.repository.Asks;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Asks")
public class Asks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private Integer qnaId;

    @Column(name = "title", length = 45, nullable = false)
    private String title;

    @Column(name = "content", length = 255, nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @Column(name = "deleted_at", nullable = false)
    private Date deletedAt;

    @Column(name = "space_id", nullable = false)
    private Integer spaceId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

}
