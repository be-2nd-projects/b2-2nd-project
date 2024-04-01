package com.example.be2ndproject.shopping_mall.repository.cart;
import com.example.be2ndproject.shopping_mall.repository.member.Member;
import com.example.be2ndproject.shopping_mall.repository.space.Space;
import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;

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
    private Member user;

    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;


}