package com.example.be2ndproject.shopping_mall.repository.member;

import com.example.be2ndproject.shopping_mall.repository.cart.Cart;
import com.example.be2ndproject.shopping_mall.repository.review.Review;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_num", nullable = false)
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "user_status")
    private boolean userStatus;

    @Column(name = "profile_img_url")
    private String profileImageUrl;

    @Column(name = "business_num")
    private Integer businessNumber;

    @Column(name = "role", nullable = false)
    private String roles;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "provider")
    private String provider;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private final List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private final List<Review> reviews = new ArrayList<>();

    public List<String> getRoleList(){
        if (this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    @Builder
    public Member(String name, String password, String email, String roles, String provider, String providerId, LocalDateTime createdAt) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.provider = provider;
        this.providerId = providerId;
        this.createdAt = createdAt;
    }


}