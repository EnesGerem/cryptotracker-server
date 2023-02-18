package com.enes.cryptotracker.error;

import com.enes.cryptotracker.error.exception.UnsupportedCurrencyCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnsupportedCurrencyCreationException.class)
    public ResponseEntity<ApiError> handleUnsupportedCurrencyCreation(UnsupportedCurrencyCreationException ex) {
        ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, ex.getClass().getName(), ex.getMessage());
        return ResponseEntity.unprocessableEntity().body(apiError);
    }
}
