package com.dogadopt.dog_adopt.domain;

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
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "shelter")
    private List<Address> address;

    @OneToMany(mappedBy = "shelter")
    private List<Dog> dogs;
}
