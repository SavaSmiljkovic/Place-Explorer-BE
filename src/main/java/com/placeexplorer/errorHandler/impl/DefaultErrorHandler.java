package com.placeexplorer.errorHandler.impl;

import com.placeexplorer.errorHandler.ErrorHandler;
import com.placeexplorer.errorHandler.ErrorMessage;
import com.placeexplorer.errorHandler.ErrorType;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DefaultErrorHandler implements ErrorHandler {

    @Resource
    private ErrorMessage errorMessage;

    @Override
    public ResponseEntity handle(ErrorType type) {
        switch (type) {
            case ORIGINAL_PLACE_NULL:
                return new ResponseEntity<>(errorMessage.getOriginalPlaceNull(), HttpStatus.NOT_FOUND);

            case ORIGINAL_PLACE_INVALID:
                return new ResponseEntity<>(errorMessage.getOriginalPlaceInvalid(), HttpStatus.UNPROCESSABLE_ENTITY);

            case UID_UNRECOGNIZED:
                return new ResponseEntity<>(errorMessage.getUid(), HttpStatus.NOT_FOUND);

            default:
                return new ResponseEntity<>(errorMessage.getGeneral(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
