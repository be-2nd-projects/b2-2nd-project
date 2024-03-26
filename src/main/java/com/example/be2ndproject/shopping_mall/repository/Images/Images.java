package com.example.be2ndproject.shopping_mall.repository.Images;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int imageId;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "space_id", nullable = false)
    private int spaceId;

    @Column(name = "thumb", length = 1, nullable = false)
    private String thumb = "0";

}
