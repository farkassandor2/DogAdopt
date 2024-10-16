package com.dogadopt.dog_adopt.service.walkingreservation;

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
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
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

            boolean isTimeInTheFuture = isTimeInTheFuture(command.getStartTime(), command.getEndTime());

            boolean isTimeWindowAlreadyTaken = checkIfTimeWindowForWalkAlreadyTaken(dog,
                                                                                    command.getStartTime(),
                                                                                    command.getEndTime());

            boolean istimeWindowValid = isTimeWindowValid(command.getStartTime(), command.getEndTime());

            if (isTimeInTheFuture) {
                if (!isTimeWindowAlreadyTaken) {
                    if (istimeWindowValid) {
                        walkingReservation.setDog(dog);
                        walkingReservation.setUser(user);
                        walkingReservationRepository.save(walkingReservation);
                        return getWalkingReservationInfo(walkingReservation, user, dog);
                    } else {
                        throw new TimeWindowNotValidException("Reserved time can be maximum " + TIME_WINDOW + " hours");
                    }
                } else {
                    throw new WalkingTimeWindowAlreadyTakenException(
                            "Sorry the required time window is already taken for dog " + dogId);
                }
            } else {
                throw new TimeWindowNotValidException("Reserved time must be in the future");
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
    public List<WalkingReservationInfo> getWalkingReservationsByUserForUser(Long userId) {
        AppUser user = appUserService.findActiveUserById(userId);
        AppUser loggedInUser = appUserService.getLoggedInCustomer();

        if (user != null && user == loggedInUser) {
            List<WalkingReservation> walkingReservations = walkingReservationRepository.findReservationForUser(userId);
            return sortListInOrderOfActiveAndOther(walkingReservations)
                                      .map(reservation -> getWalkingReservationInfo(reservation, reservation.getUser(), reservation.getDog()))
                                      .toList();
        } else {
            throw new WrongCredentialsException("Wrong credentials.");
        }
    }

    @Override
    public List<WalkingReservationInfo> getWalkingReservationsByUserForDog(Long userId, Long dogId) {

        AppUser user = appUserService.findActiveUserById(userId);
        AppUser loggedInUser = appUserService.getLoggedInCustomer();

        if (user != null && user == loggedInUser) {
            List<WalkingReservation> walkingReservations = walkingReservationRepository.findReservationForDog(dogId);
            AppUser blankUser = new AppUser();

            return sortListInOrderOfActiveAndOther(walkingReservations)
                                      .map(reservation -> {
                                          if (user.equals(reservation.getUser())) {
                                              return getWalkingReservationInfo(reservation, reservation.getUser(), reservation.getDog());
                                          } else {
                                              return getWalkingReservationInfo(reservation, blankUser, reservation.getDog());
                                          }
                                      })
                                      .toList();
        } else {
            throw new  WrongCredentialsException("Wrong credentials.");
        }
    }

    private Stream<WalkingReservation> sortListInOrderOfActiveAndOther(List<WalkingReservation> walkingReservations) {
        return walkingReservations.stream()
                                  .sorted((r1, r2) -> {
                                      if (r1.getReservationStatus() == ReservationStatus.ACTIVE && r2.getReservationStatus() != ReservationStatus.ACTIVE) {
                                          return -1;
                                      } else if (r1.getReservationStatus() != ReservationStatus.ACTIVE && r2.getReservationStatus() == ReservationStatus.ACTIVE) {
                                          return 1;
                                      }
                                      return 0;
                                  });
    }

    @Override
    public void deleteWalkingReservationByAdmin(Long reservationId) {
        walkingReservationRepository.deleteReservationOfUserByAdmin(reservationId);
    }

    @Override
    public List<WalkingReservationInfo> getAllReservationsByAdmin() {
        List<WalkingReservation> walkingReservations = walkingReservationRepository.findAll();
        return walkingReservations.stream()
                                  .map(reservation -> getWalkingReservationInfo(reservation, reservation.getUser(), reservation.getDog()))
                                  .toList();
    }

    @Override
    public List<WalkingReservationInfo> getAllReservationsByAdminForUser(Long userId) {
        List<WalkingReservation> walkingReservations = walkingReservationRepository.findReservationForUser(userId);
        return walkingReservations.stream()
                                  .map(reservation -> getWalkingReservationInfo(reservation, reservation.getUser(), reservation.getDog()))
                                  .toList();
    }

    @Override
    public List<WalkingReservationInfo> getWalkingReservationsByAdminForDog(Long dogId) {
        List<WalkingReservation> walkingReservations = walkingReservationRepository.findReservationForDog(dogId);
        return walkingReservations.stream()
                                  .map(reservation -> getWalkingReservationInfo(reservation, reservation.getUser(), reservation.getDog()))
                                  .toList();
    }

    @Override
    public WalkingReservationInfo changeReservationTime(Long userId,
                                                        Long reservationId,
                                                        WalkingReservationCreateUpdateCommand command) {

        WalkingReservation walkingReservation = getWalkingReservation(reservationId);
        AppUser user = appUserService.findActiveUserById(userId);
        AppUser loggedInUser = appUserService.getLoggedInCustomer();

        if (user != null && user == loggedInUser && walkingReservation != null) {
            AppUser userOfReservation = walkingReservation.getUser();

            if (user == userOfReservation) {
                Dog dog = walkingReservation.getDog();
                LocalDateTime startTime = command.getStartTime();
                LocalDateTime endTime = command.getEndTime();
                boolean timeWindowValid = isTimeWindowValid(startTime, endTime);

                if (timeWindowValid) {
                    walkingReservation.setStartTime(startTime);
                    walkingReservation.setEndTime(endTime);
                    walkingReservationRepository.save(walkingReservation);
                    return getWalkingReservationInfo(walkingReservation, user, dog);
                } else {
                    throw new TimeWindowNotValidException("Reserved time can be maximum " + TIME_WINDOW + " hours");
                }
            } else {
                throw new WrongCredentialsException(
                        "User with id " + userId + " does not belong to reservation with id " + reservationId);
            }
        } else {
            throw new WalkingReservationNotPossibleException("Walking reservation cannot be accomplished");
        }
    }

    public void setWalkingStatusToFulfilled() {
        try (Stream<WalkingReservation> reservations = walkingReservationRepository.streamAllReservation(LocalDateTime.now())) {
            reservations.forEach(reservation -> reservation.setReservationStatus(ReservationStatus.FULFILLED));
        } catch (Exception e) {
            log.info("Failed to update reservation: {}.", e.getMessage());
        }
    }

    private WalkingReservation getWalkingReservation(Long reservationId) {
        return walkingReservationRepository
                .findById(reservationId)
                .orElseThrow(() -> new WalkingReservationNotFoundException("Reservation not found with with id " + reservationId));
    }

    private boolean checkIfTimeWindowForWalkAlreadyTaken(Dog dog, LocalDateTime start, LocalDateTime end) {
        return walkingReservationRepository.checkIfTimeWindowForWalkAlreadyTaken(dog, start, end);
    }

    private boolean isTimeWindowValid(LocalDateTime startTime, LocalDateTime endTime) {
        long minutesBetween = Duration.between(startTime, endTime).toMinutes();
        return minutesBetween < 241;
    }

    private boolean isTimeInTheFuture(LocalDateTime startTime, LocalDateTime endTime) {
        return startTime.isAfter(LocalDateTime.now()) && endTime.isAfter(startTime);
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
