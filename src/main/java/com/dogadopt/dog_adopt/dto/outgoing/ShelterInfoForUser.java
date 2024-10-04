package com.dogadopt.dog_adopt.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShelterInfoForUser {

    private Long id;

    private String name;

    private String email;

    private String countryTelephoneCode;

    private String phoneNumber;

    private String description;

    private String websiteUrl;

    private List<AddressInfo> addressInfos;

    private String imageUrl;
}
