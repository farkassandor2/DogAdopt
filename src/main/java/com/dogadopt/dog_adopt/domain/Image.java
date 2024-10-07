package com.dogadopt.dog_adopt.domain;

import com.dogadopt.dog_adopt.domain.enums.image.ImageType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imgUrl;

    @NonNull
    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    private LocalDateTime createdAt;

    private boolean isFirstPicture;

    @ManyToOne
    @JoinColumn(name = "dog-id")
    private Dog dog;

    @ManyToOne
    @JoinColumn(name = "user-id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "shelter-id")
    private Shelter shelter;

    public Image(String imageUrl, ImageType imageType, String name, boolean isFirstPicture) {
        this.imgUrl = imageUrl;
        this.imageType = imageType;
        this.createdAt = LocalDateTime.now();
        this.name = name;
        this.isFirstPicture = isFirstPicture;
    }
}
