package com.dogadopt.dog_adopt.service.shelter;

import com.dogadopt.dog_adopt.repository.ShelterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ShelterServiceImpl implements ShelterService{

    private final ShelterRepository shelterRepository;
    private final ModelMapper modelMapper;
}
