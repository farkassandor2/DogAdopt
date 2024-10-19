package com.dogadopt.dog_adopt.service.donation;

import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.domain.Donation;
import com.dogadopt.dog_adopt.domain.enums.payment.PaymentStatus;
import com.dogadopt.dog_adopt.dto.incoming.DonationCreateCommand;
import com.dogadopt.dog_adopt.repository.DonationRepository;
import com.paypal.api.payments.Payment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService{

    private final DonationRepository donationRepository;

    @Override
    public void saveDonation(Payment payment, DonationCreateCommand donationCreateCommand, AppUser user) {
        Donation donation = new Donation();
        donation.setTotalAmount(new BigDecimal(payment.getTransactions().get(0).getAmount().getTotal()));
        donation.setTransactionId(payment.getId());
        donation.setPaymentStatus(PaymentStatus.APPROVED);
        donation.setUser(user);
        donation.setCurrency(donationCreateCommand.getCurrency());
        donation.setPaymentMethod(donationCreateCommand.getPaymentMethod());
        donationRepository.save(donation);
    }

    @Override
    public void saveAnonymousDonation(Payment payment, DonationCreateCommand donationCreateCommand) {
        Donation donation = new Donation();
        donation.setTotalAmount(new BigDecimal(payment.getTransactions().get(0).getAmount().getTotal()));
        donation.setTransactionId(payment.getId());
        donation.setPaymentStatus(PaymentStatus.APPROVED);

        donation.setCurrency(donationCreateCommand.getCurrency());
        donation.setPaymentMethod(donationCreateCommand.getPaymentMethod());
        donationRepository.save(donation);
    }
}
