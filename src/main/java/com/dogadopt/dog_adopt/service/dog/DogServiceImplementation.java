package com.dogadopt.dog_adopt.service.dog;

import com.dogadopt.dog_adopt.dto.dog.DogCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.dog.DogInfo;
import com.dogadopt.dog_adopt.repository.DogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DogServiceImplementation implements DogService{

    private final DogRepository dogRepository;

    @Override
    public DogInfo registerDog(DogCreateUpdateCommand command) {
        return null;
    }
}
