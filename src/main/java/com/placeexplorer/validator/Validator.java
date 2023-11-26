package com.placeexplorer.validator;

import com.placeexplorer.model.dto.PlaceOriginalDTO;

public interface Validator {

    /**
     * Checks if DTO object has all valid fields.
     *
     * @param placeOriginalDTO DTO that Open API returns as response to this application
     */
    boolean isValid(PlaceOriginalDTO placeOriginalDTO);

}
