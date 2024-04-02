package com.example.be2ndproject.shopping_mall.repository.cart;
import com.example.be2ndproject.shopping_mall.repository.Space.Space;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer cartId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;


}