package com.dogadopt.dog_adopt.domain;

import com.dogadopt.dog_adopt.domain.enums.image.ImageType;
import jakarta.persistence.*;
import lombok.*;

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
    private ImageType imageType;

    @ManyToOne
    @JoinColumn(name = "dog-id")
    private Dog dog;

    @ManyToOne
    @JoinColumn(name = "user-id")
    private AppUser user;

    public Image(String imageUrl, String originalFilename) {
        this.url = imageUrl;
        this.name = originalFilename;
    }
}
