package com.dogadopt.dog_adopt.paypal;

import com.paypal.base.rest.APIContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PaypalServiceImpl implements PaypalService{

    private final APIContext apiContext;




}
