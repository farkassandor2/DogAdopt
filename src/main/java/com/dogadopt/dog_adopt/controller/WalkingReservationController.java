package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.service.walkingreservation.WalkingReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/walking")
@RequiredArgsConstructor
@Slf4j
public class WalkingReservationController {

    private final WalkingReservationService walkingReservationService;
}
