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

    private String url;

    @NonNull
    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "dog-id")
    private Dog dog;

    @ManyToOne
    @JoinColumn(name = "user-id")
    private AppUser user;

    public Image(String imageUrl, ImageType imageType, String name) {
        this.url = imageUrl;
        this.imageType = imageType;
        this.createdAt = LocalDateTime.now();
        this.name = name;
    }
}
