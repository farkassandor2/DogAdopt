package com.dogadopt.dog_adopt.paypal;

import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.domain.enums.payment.Currency;
import com.dogadopt.dog_adopt.domain.enums.payment.PaymentMethod;
import com.dogadopt.dog_adopt.dto.incoming.DonationCreateCommand;
import com.dogadopt.dog_adopt.service.donation.DonationService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/paypal")
@RequiredArgsConstructor
@Slf4j
public class PayPalController {

    private final PayPalService paypalService;
    private final DonationService donationService;

    @PostMapping("/pay")
    public String pay(@RequestBody DonationCreateCommand donationCreateCommand) throws PayPalRESTException {
        String cancelUrl = "http://localhost:8080/api/paypal/cancel";
        String successUrl = "http://localhost:8080/api/paypal/success";

        return paypalService.createPayment(donationCreateCommand.getTotalAmount(),
                                           donationCreateCommand.getCurrency().toString(),
                                           "paypal",
                                           "sale",
                                           "DogAdopt Donation",
                                           cancelUrl,
                                           successUrl);
    }

    @GetMapping("/success")
    public String successPay(@RequestParam Map<String, String> params) throws PayPalRESTException {
        String paymentId = params.get("paymentId");
        String payerId = params.get("PayerID");

        Payment payment = paypalService.executePayment(paymentId, payerId);

        if (payment.getState().equals("approved")) {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            AppUser user = null;
            if (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String)) {
                user = (AppUser) auth.getPrincipal();
            }

            DonationCreateCommand donationCreateCommand = new DonationCreateCommand(
                    new BigDecimal(payment.getTransactions().get(0).getAmount().getTotal()),
                    PaymentMethod.PAYPAL,
                    Currency.valueOf(payment.getTransactions().get(0).getAmount().getCurrency())
            );

            if (user != null) {
                donationService.saveDonation(payment, donationCreateCommand, user);
            } else {
                donationService.saveAnonymousDonation(payment, donationCreateCommand);
            }

            return "Payment Success";
        }
        return "Payment Failed";
    }

    @GetMapping("/cancel")
    public String cancelPay() {
        return "Payment cancelled";
    }
}
