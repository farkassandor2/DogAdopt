package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.dto.incoming.AppUserUpdateCommand;
import com.dogadopt.dog_adopt.dto.incoming.ImageUploadCommand;
import com.dogadopt.dog_adopt.dto.outgoing.AppUserInfo;
import com.dogadopt.dog_adopt.service.user.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @PatchMapping("/update/{userId}")
    @ResponseStatus(OK)
    public AppUserInfo updateUser(@PathVariable Long userId,
                                  @Valid @RequestBody AppUserUpdateCommand command) {
        log.info("Http request, POST / api / users / update , body: {}", command.toString());
        return appUserService.updateUser(userId, command);
    }

    @PutMapping("/upload-picture/{userId}")
    @ResponseStatus(OK)
    public void uploadPictureForUser(@PathVariable Long userId, @ModelAttribute ImageUploadCommand command) {
        log.info("Http request / PUT / api / users / upload-picture / userId, body {}", command.toString());
        appUserService.uploadPictureForUser(userId, command);
    }
}
