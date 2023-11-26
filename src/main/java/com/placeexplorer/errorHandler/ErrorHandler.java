package com.placeexplorer.errorHandler;

import org.springframework.http.ResponseEntity;

public interface ErrorHandler {

    /**
     * Handles all of the expected errors and returns appropriate ResponseEntity.
     *
     * @param type Type of the error that error-handler should recognize and process
     */
    ResponseEntity handle(ErrorType type);

}
