package com.dogadopt.dog_adopt.exception;

import com.dogadopt.dog_adopt.exception.error.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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
}
