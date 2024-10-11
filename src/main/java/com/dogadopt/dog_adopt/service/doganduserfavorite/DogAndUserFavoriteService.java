package com.dogadopt.dog_adopt.service.doganduserfavorite;

import com.dogadopt.dog_adopt.dto.outgoing.DogAndUserFavoriteInfo;

public interface DogAndUserFavoriteService {

    DogAndUserFavoriteInfo addNewFavorite(Long userId, Long dogId);

    void deleteDogFromFavorite(Long userId, Long dogId);
}
