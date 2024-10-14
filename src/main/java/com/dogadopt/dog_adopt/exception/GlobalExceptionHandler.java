package com.dogadopt.dog_adopt.exception;

import com.dogadopt.dog_adopt.exception.error.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public List<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        List<ErrorResponse> validationErrors = exception.getBindingResult()
                                                        .getFieldErrors().stream()
                                                        .map(fieldError -> new ErrorResponse(fieldError.getField(),
                                                                                             fieldError.getDefaultMessage()))
                                                        .toList();
        log.error(exception.getMessage());
        return validationErrors;
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, BAD_REQUEST);
    }

    @ExceptionHandler(CloudinaryException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleCloudinaryException(CloudinaryException ex) {
        return Collections.singletonList(new ErrorResponse("cloudinary", ex.getMessage()));
    }

    @ExceptionHandler(ZipInvalidException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleZipInvalidException(ZipInvalidException ex) {
        return Collections.singletonList(new ErrorResponse("zip", ex.getMessage()));
    }

    @ExceptionHandler(ShelterAlreadyRegisteredException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleShelterAlreadyRegisteredException(ShelterAlreadyRegisteredException ex) {
        return Collections.singletonList(new ErrorResponse("shelter", ex.getMessage()));
    }

    @ExceptionHandler(DogNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleDogNotFoundException(DogNotFoundException ex) {
        return Collections.singletonList(new ErrorResponse("dogId", ex.getMessage()));
    }

    @ExceptionHandler(ShelterNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleShelterNotFoundException(ShelterNotFoundException ex) {
        return Collections.singletonList(new ErrorResponse("shelterId", ex.getMessage()));
    }

    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleAddressNotFoundException(AddressNotFoundException ex) {
        return Collections.singletonList(new ErrorResponse("addressId", ex.getMessage()));
    }

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleImageNotFoundException(ImageNotFoundException ex) {
        return Collections.singletonList(new ErrorResponse("imageId", ex.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return Collections.singletonList(new ErrorResponse("email", ex.getMessage()));
    }

    @ExceptionHandler(WrongCountryNameException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleWrongCountryNameException(WrongCountryNameException ex) {
        return Collections.singletonList(new ErrorResponse("countryName", ex.getMessage()));
    }

    @ExceptionHandler(TokenNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleTokenNotFoundException(TokenNotFoundException ex) {
        return Collections.singletonList(new ErrorResponse("token", ex.getMessage()));
    }

    @ExceptionHandler(TokenHasAlreadyBeenConfirmed.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleTokenHasAlreadyBeenConfirmed(TokenHasAlreadyBeenConfirmed ex) {
        return Collections.singletonList(new ErrorResponse("token", ex.getMessage()));
    }

    @ExceptionHandler(TokesHasAlreadyExpired.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleTokesHasAlreadyExpired(TokesHasAlreadyExpired ex) {
        return Collections.singletonList(new ErrorResponse("token", ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return Collections.singletonList(new ErrorResponse("id", ex.getMessage()));
    }

    @ExceptionHandler(UserNotActiveException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleUserNotActiveException(UserNotActiveException ex) {
        return Collections.singletonList(new ErrorResponse("userId", ex.getMessage()));
    }

    @ExceptionHandler(DogCannotBeAddedToFavoritesException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleDogCannotBeAddedToFavoritesException(DogCannotBeAddedToFavoritesException ex) {
        return Collections.singletonList(new ErrorResponse("dogId", ex.getMessage()));
    }

    @ExceptionHandler(DogIsAlreadyOnFavoriteListOfUserException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleDogIsAlreadyOnFavoriteListOfUserException(DogIsAlreadyOnFavoriteListOfUserException ex) {
        return Collections.singletonList(new ErrorResponse("dogId", ex.getMessage()));
    }

    @ExceptionHandler(DogIsNotOnTheFavoriteListOfUserException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleDogIsNotOnTheFavoriteListOfUserException(DogIsNotOnTheFavoriteListOfUserException ex) {
        return Collections.singletonList(new ErrorResponse("dogId", ex.getMessage()));
    }

    @ExceptionHandler(DogCannotBeRemovedFromFavoritesException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleDogCannotBeRemovedFromFavoritesException(DogCannotBeRemovedFromFavoritesException ex) {
        return Collections.singletonList(new ErrorResponse("dogId", ex.getMessage()));
    }

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleCommentNotFoundException(CommentNotFoundException ex) {
        return Collections.singletonList(new ErrorResponse("commentId", ex.getMessage()));
    }

    @ExceptionHandler(CommentDoesNotBelongToUserException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleCommentDoesNotBelongToUserException(CommentDoesNotBelongToUserException ex) {
        return Collections.singletonList(new ErrorResponse("commentId", ex.getMessage()));
    }

    @ExceptionHandler(IncorrectUsernameOrPasswordException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleIncorrectUsernameOrPasswordException(IncorrectUsernameOrPasswordException ex) {
        return Collections.singletonList(new ErrorResponse("message", ex.getMessage()));
    }

    @ExceptionHandler(AccountHasNotBeenActivatedYetException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleAccountHasNotBeenActivatedYetException(AccountHasNotBeenActivatedYetException ex) {
        return Collections.singletonList(new ErrorResponse("message", ex.getMessage()));
    }

    @ExceptionHandler(DogAlreadyAdoptedInRealLifeException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleDogAlreadyAdoptedInRealLifeException(DogAlreadyAdoptedInRealLifeException ex) {
        return Collections.singletonList(new ErrorResponse("dogId", ex.getMessage()));
    }

    @ExceptionHandler(WrongCredentialsException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleWrongCredentialsException(WrongCredentialsException ex) {
        return Collections.singletonList(new ErrorResponse("message", ex.getMessage()));
    }

    @ExceptionHandler(AdoptionNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleAdoptionNotFoundException(AdoptionNotFoundException ex) {
        return Collections.singletonList(new ErrorResponse("adoptionId", ex.getMessage()));
    }

    @ExceptionHandler(CannotDeleteAdoptionException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleCannotDeleteAdoptionException(CannotDeleteAdoptionException ex) {
        return Collections.singletonList(new ErrorResponse("adoptionId", ex.getMessage()));
    }

    @ExceptionHandler(WalkingReservationNotPossibleException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleWalkingReservationNotPossibleException(WalkingReservationNotPossibleException ex) {
        return Collections.singletonList(new ErrorResponse("dogId", ex.getMessage()));
    }

    @ExceptionHandler(WalkingTimeWindowAlreadyTakenException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleWalkingTimeWindowAlreadyTakenException(WalkingTimeWindowAlreadyTakenException ex) {
        return Collections.singletonList(new ErrorResponse("dogId", ex.getMessage()));
    }

    @ExceptionHandler(ReservationDoesNotBelongToUserException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleReservationDoesNotBelongToUserException(ReservationDoesNotBelongToUserException ex) {
        return Collections.singletonList(new ErrorResponse("reservationId", ex.getMessage()));
    }

    @ExceptionHandler(UserHasALreadyHaveReservedWalkingException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleUserHasALreadyHaveReservedWalkingException(UserHasALreadyHaveReservedWalkingException ex) {
        return Collections.singletonList(new ErrorResponse("userId", ex.getMessage()));
    }

    @ExceptionHandler(TimeWindowNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleTimeWindowNotValidException(TimeWindowNotValidException ex) {
        return Collections.singletonList(new ErrorResponse("message", ex.getMessage()));
    }

    @ExceptionHandler(WalkingReservationNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleWalkingReservationNotFoundException(WalkingReservationNotFoundException ex) {
        return Collections.singletonList(new ErrorResponse("reservationId", ex.getMessage()));
    }
}
