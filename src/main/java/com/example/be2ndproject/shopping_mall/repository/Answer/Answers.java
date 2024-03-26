package com.example.be2ndproject.shopping_mall.repository.Answer;
import com.example.be2ndproject.shopping_mall.repository.Asks.Asks;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Answers")
public class Answers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private int answerId;

    @ManyToOne
    @JoinColumn(name = "qna_id", referencedColumnName = "qna_id", nullable = false)
    private Asks ask;

    @Column(name = "content", length = 255, nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @Column(name = "deleted_at", nullable = false)
    private Date deletedAt;

}