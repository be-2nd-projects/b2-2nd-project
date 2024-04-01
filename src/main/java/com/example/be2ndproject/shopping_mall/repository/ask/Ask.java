package com.example.be2ndproject.shopping_mall.repository.ask;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Asks")
public class Ask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private Integer qnaId;

    @Column(name = "title", length = 45, nullable = false)
    private String title;

    @Column(name = "content", length = 255, nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", nullable = false)
    private LocalDateTime deletedAt;

    @Column(name = "space_id", nullable = false)
    private Integer spaceId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

}
