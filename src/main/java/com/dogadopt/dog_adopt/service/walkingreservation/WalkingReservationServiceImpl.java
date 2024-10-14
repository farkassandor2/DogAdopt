package com.dogadopt.dog_adopt.service.walkingreservation;

import com.dogadopt.dog_adopt.repository.WalkingReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class WalkingReservationServiceImpl implements WalkingReservationService{

    private final WalkingReservationRepository walkingReservationRepository;
    private final ModelMapper modelMapper;
}
