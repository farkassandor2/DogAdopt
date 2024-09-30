package com.dogadopt.dog_adopt.domain;

import com.dogadopt.dog_adopt.domain.enums.payment.PaymentMethod;
import com.dogadopt.dog_adopt.domain.enums.payment.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalAmount;

    private BigDecimal amountToShelter;

    private BigDecimal amountToEgi;

    private LocalDateTime donationDate;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    @ManyToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;

}
