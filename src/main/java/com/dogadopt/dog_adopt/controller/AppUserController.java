package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.service.user.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
}
