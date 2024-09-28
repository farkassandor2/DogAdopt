package com.dogadopt.dog_adopt.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;

    @ManyToOne
    @JoinColumn(name = "dog-id")
    private Dog dog;

    public Image(String imageUrl, String originalFilename) {
        this.url = imageUrl;
        this.name = originalFilename;
    }
}
