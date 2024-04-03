package com.example.be2ndproject.shopping_mall.repository.space;

import com.example.be2ndproject.shopping_mall.repository.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.example.be2ndproject.shopping_mall.repository.Image.Image;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Spaces")
public class Space {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "space_id")
    private Integer spaceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Member member;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private List<Image> images;

    @Column(name = "space_name")
    private String spaceName;

    @Column(name = "space_address")
    private String spaceAddress;

    @Column(name = "price")
    private Integer price;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "area")
    private String area;

    @Column(name = "category")
    private String category;

    @Column(name = "capacity_num")
    private Integer capacityNum;

    @Column(name = "space_status")
    private String spaceStatus;

    @Column(name = "score")
    private Float score;

    @Setter
    @Column(name = "stock")
    private int stock;
}
