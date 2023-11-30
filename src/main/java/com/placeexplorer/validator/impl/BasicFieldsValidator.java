package com.placeexplorer.validator.impl;

import com.placeexplorer.model.dto.PlaceOriginalDTO;
import com.placeexplorer.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BasicFieldsValidator implements Validator {

    private final Logger logger = LoggerFactory.getLogger(BasicFieldsValidator.class);

    @Override
    public boolean isValid(PlaceOriginalDTO placeOriginalDTO) {
        if (isUndefined(placeOriginalDTO.getEntryType()) ||
            isUndefined(placeOriginalDTO.getLocalEntryId()) ||
            isUndefined(placeOriginalDTO.getDisplayedWhat()) ||
            isUndefined(placeOriginalDTO.getDisplayedWhere()) ||
            Objects.isNull(placeOriginalDTO.getPlaceFeedbackSummary()) ||
            placeOriginalDTO.getPlaceFeedbackSummary().getAverageRating() < 0.0 ||
            placeOriginalDTO.getPlaceFeedbackSummary().getAverageRating() > 5.0) {
            logger.error("Some basic fields are not defined");
            return false;
        }

        return true;
    }

    private boolean isUndefined(String value) {
        return Objects.isNull(value) || value.isBlank();
    }

}
