package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.dto.incoming.WalkingReservationCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.WalkingReservationInfo;
import com.dogadopt.dog_adopt.service.walkingreservation.WalkingReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/walking")
@RequiredArgsConstructor
@Slf4j
public class WalkingReservationController {

    private final WalkingReservationService walkingReservationService;

    @PostMapping("/reservation/{userId}/{dogId}")
    @ResponseStatus(CREATED)
    public WalkingReservationInfo createWalkingReservation(@PathVariable Long userId,
                                                           @PathVariable Long dogId,
                                                           @Valid @RequestBody WalkingReservationCreateUpdateCommand command) {
        log.info("Http request / POST / api / walking / reservation / userId / dogId, body: {}", command.toString());
        return walkingReservationService.createWalkingReservation(userId, dogId, command);
    }

    @DeleteMapping("/delete/{userId}/{reservationId}")
    @ResponseStatus(OK)
    public void deleteWalkingReservationByUser(@PathVariable Long userId, @PathVariable Long reservationId) {
        log.info("Http request / DELETE / api / walking / delete / userId / reservationId");
        walkingReservationService.deleteWalkingReservationByUser(userId, reservationId);
    }

    @PatchMapping("/change-time/{userId}/{reservationId}")
    @ResponseStatus(OK)
    public WalkingReservationInfo changeReservationTime(@PathVariable Long userId,
                                                        @PathVariable Long reservationId,
                                                        @RequestBody WalkingReservationCreateUpdateCommand command) {
        log.info("Http request / PATCH / api / walking / change-time / userId / reservationId, body: {}", command.toString());
        return walkingReservationService.changeReservationTime(userId, reservationId, command);
    }

    @GetMapping("/{userId}/{dogId}")
    @ResponseStatus(OK)
    public List<WalkingReservationInfo> getWalkingReservationsByUserForDog(@PathVariable Long userId,
                                                                           @PathVariable Long dogId) {
        log.info("Http request / GET / api / walking / userId / dogId");
        return walkingReservationService.getWalkingReservationsByUserForDog(userId, dogId);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(OK)
    public List<WalkingReservationInfo> getWalkingReservationsByUserForUser(@PathVariable Long userId) {
        log.info("Http request / GET / api / walking / userId");
        return walkingReservationService.getWalkingReservationsByUserForUser(userId);
    }

    @DeleteMapping("/admin/delete/{reservationId}")
    @ResponseStatus(OK)
    public void deleteWalkingReservationByAdmin(@PathVariable Long reservationId) {
        log.info("Http request / DELETE / api / walking / admin / delete / reservationId");
        walkingReservationService.deleteWalkingReservationByAdmin(reservationId);
    }

    @GetMapping("/admin/reservations")
    @ResponseStatus(OK)
    public List<WalkingReservationInfo> getAllReservationsByAdmin() {
        log.info("Http request / GET / api / walking / admin / reservations");
        return walkingReservationService.getAllReservationsByAdmin();
    }

    @GetMapping("/admin/reservations/{userId}")
    @ResponseStatus(OK)
    public List<WalkingReservationInfo> getAllReservationsByAdminForUser(@PathVariable Long userId) {
        log.info("Http request / GET / api / walking / admin / reservations / userId");
        return walkingReservationService.getAllReservationsByAdminForUser(userId);
    }

    @GetMapping("/admin/{dogId}")
    @ResponseStatus(OK)
    public List<WalkingReservationInfo> getWalkingReservationsByAdminForDog(@PathVariable Long dogId) {
        log.info("Http request / GET / api / walking / admin / dogId");
        return walkingReservationService.getWalkingReservationsByAdminForDog(dogId);
    }
}
