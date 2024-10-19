package com.dogadopt.dog_adopt.service.donation;

import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.dto.incoming.DonationCreateCommand;
import com.paypal.api.payments.Payment;

public interface DonationService {

    void saveDonation(Payment payment, DonationCreateCommand donationCreateCommand, AppUser user);

    void saveAnonymousDonation(Payment payment, DonationCreateCommand donationCreateCommand);
}
