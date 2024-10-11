package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.dto.outgoing.DogAndUserFavoriteInfo;
import com.dogadopt.dog_adopt.service.doganduserfavorite.DogAndUserFavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@Slf4j
public class DogAndUserFavoriteController {

    private final DogAndUserFavoriteService dogAndUserFavoriteService;

    @PostMapping("/{userId}/{dogId}")
    @ResponseStatus(CREATED)
    public DogAndUserFavoriteInfo addNewFavorite(@PathVariable Long userId, @PathVariable Long dogId) {
        log.info("Http request / POST / api / favorites / userId/ dogId, body ");
        return dogAndUserFavoriteService.addNewFavorite(userId, dogId);
    }
}
