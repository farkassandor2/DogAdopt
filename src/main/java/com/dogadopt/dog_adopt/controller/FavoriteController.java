package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.dto.outgoing.FavoriteInfo;
import com.dogadopt.dog_adopt.service.favorite.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@Slf4j
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{userId}/{dogId}")
    @ResponseStatus(CREATED)
    public FavoriteInfo addNewFavorite(@PathVariable Long userId, @PathVariable Long dogId) {
        log.info("Http request / POST / api / favorites / userId/ dogId");
        return favoriteService.addNewFavorite(userId, dogId);
    }

    @DeleteMapping("/{userId}/{dogId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteDogFromFavorite(@PathVariable Long userId, @PathVariable Long dogId) {
        log.info("Http request / DELETE / api / favorites / userId/ dogId");
        favoriteService.deleteDogFromFavorite(userId, dogId);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(OK)
    public List<FavoriteInfo> getFavoritesOfUser(@PathVariable Long userId) {
        log.info("Http request / GET / api / favorites / userId");
        return favoriteService.getFavoritesOfUser(userId);
    }
}
