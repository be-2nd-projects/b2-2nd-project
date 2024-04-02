package com.example.be2ndproject.shopping_mall.repository.answer;
import com.example.be2ndproject.shopping_mall.repository.ask.Ask;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import com.example.be2ndproject.shopping_mall.repository.space.Space;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Integer answerId;

    @ManyToOne
    @JoinColumn(name = "qna_id", referencedColumnName = "qna_id")
    private Ask ask;

    @Column(name = "content", length = 255, nullable = false)
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member user;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;

    public Answer(Ask ask, String content, LocalDateTime createdAt, LocalDateTime updatedAt,Member user) {
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.ask = ask;
    }

}