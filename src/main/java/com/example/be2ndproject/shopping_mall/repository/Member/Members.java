package com.example.be2ndproject.shopping_mall.repository.Member;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Members")
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

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

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", nullable = false)
    private LocalDateTime deletedAt;

    @Column(name = "user_status", nullable = false)
    private boolean userStatus;

    @Column(name = "profile_img_url")
    private String profileImageUrl;

    @Column(name = "business_num")
    private Integer businessNumber;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "provider")
    private String provider;

}