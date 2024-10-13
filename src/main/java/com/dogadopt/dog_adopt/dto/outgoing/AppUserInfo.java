package com.dogadopt.dog_adopt.dto.outgoing;

import com.dogadopt.dog_adopt.domain.enums.address.Country;
import com.dogadopt.dog_adopt.domain.enums.user.UserLevel;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserInfo {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String countryTelephoneCode;

    private String phoneNumber;

    private Country country;

    private List<ImageInfo> imageInfos;

    private UserLevel userLevel;

    private LocalDateTime createdAt;

    private List<AdoptionInfo> adoptionInfos;

    private List<FavoriteInfo> favoriteInfos;

//    private List<DonationInfo> donationInfos;

//    private List<WalkingReservationInfo> walkingReservationInfos;

}
