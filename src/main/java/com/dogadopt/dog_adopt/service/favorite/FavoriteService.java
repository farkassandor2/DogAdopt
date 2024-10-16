package com.dogadopt.dog_adopt.service.favorite;

import com.dogadopt.dog_adopt.dto.outgoing.FavoriteInfo;

import java.util.List;

public interface FavoriteService {

    FavoriteInfo addNewFavorite(Long userId, Long dogId);

    void deleteDogFromFavorite(Long userId, Long dogId);

    List<FavoriteInfo> getFavoritesOfUser(Long userId);
}
