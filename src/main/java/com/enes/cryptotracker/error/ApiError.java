package com.enes.cryptotracker.error;

import org.springframework.http.HttpStatus;

public record ApiError(HttpStatus status, String exception, String message) {

}
