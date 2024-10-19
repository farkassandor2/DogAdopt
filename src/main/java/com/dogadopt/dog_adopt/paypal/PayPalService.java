package com.dogadopt.dog_adopt.paypal;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.math.BigDecimal;

public interface PayPalService {

    String createPayment(BigDecimal totalAmount, String currency, String paypal, String sale, String dogAdoptDonation, String cancelUrl, String successUrl) throws PayPalRESTException;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
}
