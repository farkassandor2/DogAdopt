package com.dogadopt.dog_adopt.service.walkingreservation;

import com.dogadopt.dog_adopt.config.ObjectMapperUtil;
import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.domain.WalkingReservation;
import com.dogadopt.dog_adopt.domain.enums.reservation.ReservationStatus;
import com.dogadopt.dog_adopt.dto.incoming.WalkingReservationCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.DogInfoOneDog;
import com.dogadopt.dog_adopt.dto.outgoing.WalkingReservationInfo;
import com.dogadopt.dog_adopt.exception.*;
import com.dogadopt.dog_adopt.repository.WalkingReservationRepository;
import com.dogadopt.dog_adopt.service.dog.DogService;
import com.dogadopt.dog_adopt.service.user.AppUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WalkingReservationServiceImpl implements WalkingReservationService{

    private static final int TIME_WINDOW = 4;

    private final WalkingReservationRepository walkingReservationRepository;
    private final AppUserService appUserService;
    private final DogService dogService;
    private final ModelMapper modelMapper;

    @Override
    public WalkingReservationInfo createWalkingReservation(Long userId,
                                                           Long dogId,
                                                           WalkingReservationCreateUpdateCommand command) {

        AppUser user = appUserService.findActiveUserById(userId);
        AppUser loggedInUser = appUserService.getLoggedInCustomer();
        Dog dog = dogService.getOneDog(dogId);
        WalkingReservation walkingReservation;

        if (user != null && user == loggedInUser && dog != null) {
            walkingReservation = modelMapper.map(command, WalkingReservation.class);
            boolean timeWindowAlreadyTaken = checkIfTimeWindowForWalkAlreadyTaken(dog,
                                                                                  command.getStartTime(),
                                                                                  command.getEndTime());

            boolean timeWindowValid = isTimeWindowValid(command.getStartTime(), command.getEndTime());

            boolean userAlreadyHasReservedWalking = checkIfUserAlreadyHasActiveReservedWalking(user);

            if (!timeWindowAlreadyTaken) {
                if (timeWindowValid) {
                    if (!userAlreadyHasReservedWalking) {
                        walkingReservation.setDog(dog);
                        walkingReservation.setUser(user);
                        walkingReservationRepository.save(walkingReservation);
                        return getWalkingReservationInfo(walkingReservation, user, dog);
                    } else {
                        throw new UserHasALreadyHaveReservedWalkingException(
                                "User with id " + userId + " already has a reserved walking time. Please delete active reservation to make a new one.");
                    }
                } else {
                    throw new TimeWindowNotValidException("Reserved time can be maximum " + TIME_WINDOW + " hours");
                }
            } else {
                throw new WalkingTimeWindowAlreadyTakenException(
                        "Sorry the required time window is already taken for dog " + dogId);
            }
        } else {
            throw new WalkingReservationNotPossibleException("Walking reservation cannot be accomplished");
        }
    }

    @Override
    public void deleteWalkingReservationByUser(Long userId, Long reservationId) {

        AppUser user = appUserService.findActiveUserById(userId);
        AppUser loggedInUser = appUserService.getLoggedInCustomer();

        if (user != null && user == loggedInUser) {
            walkingReservationRepository.deleteReservationOfUserByUser(user, reservationId);
        } else {
            throw new ReservationDoesNotBelongToUserException(
                    "Reservation with id " + reservationId + " does not belong to user with id " + userId);
        }
    }

    @Override
    public void deleteWalkingReservationByAdmin(Long reservationId) {
        walkingReservationRepository.deleteReservationOfUserByAdmin(reservationId);
    }

    @Override
    public List<WalkingReservationInfo> getAllReservationsByAdmin() {
        List<WalkingReservation> walkingReservations = walkingReservationRepository.findAll();
        return ObjectMapperUtil.mapAll(walkingReservations, WalkingReservationInfo.class);
    }

    @Override
    public List<WalkingReservationInfo> getAllReservationsByAdminForUser(Long userId) {
        List<WalkingReservation> walkingReservations = walkingReservationRepository.findReservationByUser(userId);
        return ObjectMapperUtil.mapAll(walkingReservations, WalkingReservationInfo.class);
    }

    @Override
    public List<WalkingReservationInfo> getAllReservationsByAdminForDog(Long dogId) {
        List<WalkingReservation> walkingReservations = walkingReservationRepository.findReservationByDog(dogId);
        return ObjectMapperUtil.mapAll(walkingReservations, WalkingReservationInfo.class);
    }

    private boolean checkIfTimeWindowForWalkAlreadyTaken(Dog dog, LocalDateTime start, LocalDateTime end) {
        return walkingReservationRepository.checkIfTimeWindowForWalkAlreadyTaken(dog, start, end);
    }

    private boolean checkIfUserAlreadyHasActiveReservedWalking(AppUser user) {
        return walkingReservationRepository.checkIfUserAlreadyHasActiveReservedWalking(user, ReservationStatus.ACTIVE);
    }

    private boolean isTimeWindowValid(LocalDateTime startTime, LocalDateTime endTime) {
        long minutesBetween = Duration.between(startTime, endTime).toMinutes();
        return minutesBetween < 241;
    }

    public WalkingReservationInfo getWalkingReservationInfo(WalkingReservation walkingReservation,
                                                            AppUser user,
                                                            Dog dog) {

        if (user != null && dog != null) {
            WalkingReservationInfo walkingReservationInfo = modelMapper.map(
                    walkingReservation, WalkingReservationInfo.class);

            walkingReservationInfo.setUserId(user.getId());
            DogInfoOneDog dogInfoOneDog = dogService.getDogInfoOneDog(dog);
            walkingReservationInfo.setDogInfoOneDog(dogInfoOneDog);
            return walkingReservationInfo;
        } else {
            throw new WalkingReservationNotPossibleException("Walking reservation cannot be accomplished");
        }
    }
}
