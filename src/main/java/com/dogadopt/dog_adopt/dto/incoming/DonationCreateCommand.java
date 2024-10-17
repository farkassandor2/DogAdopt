package com.dogadopt.dog_adopt.dto.incoming;

import com.dogadopt.dog_adopt.domain.enums.payment.Currency;
import com.dogadopt.dog_adopt.domain.enums.payment.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonationCreateCommand {

    @Min(1)
    @NotNull
    private BigDecimal totalAmount;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private Currency currency;
}
