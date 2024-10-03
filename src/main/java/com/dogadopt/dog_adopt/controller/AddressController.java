package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.service.address.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/addresses")
@Slf4j
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
}
