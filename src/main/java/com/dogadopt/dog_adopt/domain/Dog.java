package com.dogadopt.dog_adopt.domain;

import com.dogadopt.dog_adopt.domain.enums.dog.DogBreed;
import com.dogadopt.dog_adopt.domain.enums.dog.DogSize;
import com.dogadopt.dog_adopt.domain.enums.dog.HealthStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    private DogBreed breed;

    private DogSize dogSize;

    private HealthStatus healthStatus;

    private String description;

    @OneToMany(mappedBy = "dog")
    private List<Image> images;
}
