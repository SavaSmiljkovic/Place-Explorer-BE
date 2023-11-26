package com.placeexplorer.validator;

import com.placeexplorer.model.dto.PlaceOriginalDTO;
import lombok.Setter;

@Setter
public class PlaceOriginalDTOValidator {

    private Validator[] validators;

    public boolean isValid(PlaceOriginalDTO placeOriginalDTO) {

        for (Validator validator : validators) {
            if (!validator.isValid(placeOriginalDTO)) {
                return false;
            }
        }

        return true;
    }

}
