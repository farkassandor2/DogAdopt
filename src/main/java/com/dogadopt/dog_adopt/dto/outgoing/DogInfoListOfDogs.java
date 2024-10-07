package com.dogadopt.dog_adopt.dto.outgoing;

import com.dogadopt.dog_adopt.domain.enums.dog.DogBreed;
import com.dogadopt.dog_adopt.domain.enums.dog.Status;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DogInfoListOfDogs {

    private Long id;

    private String name;

    private Integer age;

    private DogBreed breed;

    private Status status;

    private ImageInfo imageInfo;

    private Long shelterId;

}
