package com.example.be2ndproject.shopping_mall.repository.Image;
import com.example.be2ndproject.shopping_mall.repository.space.Space;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer imageId;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "space_id", nullable = false)
    private Integer spaceId;

    @Column(name = "thumb", length = 1, nullable = false)
    private String thumb = "0";
}
