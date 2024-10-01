package com.dogadopt.dog_adopt.exception;

import com.dogadopt.dog_adopt.exception.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;

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

    @ExceptionHandler(CloudinaryException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ErrorResponse> handleCloudinaryException(CloudinaryException ex) {
        return Collections.singletonList(new ErrorResponse("message", ex.getMessage()));
    }
}
