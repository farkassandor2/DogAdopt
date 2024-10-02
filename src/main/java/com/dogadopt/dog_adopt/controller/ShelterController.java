package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.service.shelter.ShelterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shelter")
@RequiredArgsConstructor
@Slf4j
public class ShelterController {

    private final ShelterService shelterService;


}
