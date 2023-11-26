package com.placeexplorer.validator.impl;

import com.placeexplorer.model.Shift;
import com.placeexplorer.model.dto.PlaceOriginalDTO;
import com.placeexplorer.model.DayName;
import com.placeexplorer.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class DayValidator implements Validator {

    private final Logger logger = LoggerFactory.getLogger(DayValidator.class);

    @Override
    public boolean isValid(PlaceOriginalDTO placeOriginalDTO) {
        if (Objects.isNull(placeOriginalDTO.getOpeningHours()) ||
            Objects.isNull(placeOriginalDTO.getOpeningHours().getDays())) {
            logger.error("OpeningHours or Days are not defined");
            return false;
        }

        for (Map.Entry<String, List<Shift>> entry : placeOriginalDTO.getOpeningHours().getDays().entrySet()) {
            if (isUndefined(entry.getKey()) ||
                Objects.isNull(entry.getValue()) ||
                entry.getValue().isEmpty()) {
                logger.error("WeekDay or Shifts are not defined");
                return false;
            }

            if (!isDayNameValid(entry.getKey())) {
                logger.error("Day Name is not appropriate: " + entry.getKey());
                return false;
            }

            for (Shift shift : entry.getValue()) {
                if (isUndefined(shift.getStart()) || isUndefined(shift.getEnd())) {
                    logger.error("Shift Start or End value is not defined");
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isUndefined(String value) {
        return Objects.isNull(value) || value.isBlank();
    }

    private boolean isDayNameValid(String dayName) {
        try {
            DayName.valueOf(dayName.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
