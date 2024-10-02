package com.dogadopt.dog_adopt.service.zip;

import com.dogadopt.dog_adopt.domain.enums.address.Country;
import jakarta.validation.constraints.NotNull;

public interface ZipcodeService {

    boolean validate(String zip, @NotNull Country country);
}
