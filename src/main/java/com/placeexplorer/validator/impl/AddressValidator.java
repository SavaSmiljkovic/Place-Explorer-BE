package com.placeexplorer.validator.impl;

import com.placeexplorer.model.dto.PlaceOriginalDTO;
import com.placeexplorer.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class AddressValidator implements Validator {

    private final Logger logger = LoggerFactory.getLogger(AddressValidator.class);

    @Override
    public boolean isValid(PlaceOriginalDTO placeOriginalDTO) {
        if (Objects.isNull(placeOriginalDTO.getAddresses()) ||
            placeOriginalDTO.getAddresses().isEmpty()) {
            logger.error("Addresses List is null or empty");
            return false;
        }

        for (PlaceOriginalDTO.Address address : placeOriginalDTO.getAddresses()) {
            if (!isValidAddress(address)) {
                logger.error("Invalid address fields");
                return false;
            }
        }

        return true;
    }

    private boolean isValidAddress(PlaceOriginalDTO.Address address) {
        return Objects.nonNull(address.getWhere()) &&
            Objects.nonNull(address.getWhere().getGeography()) &&
            Objects.nonNull(address.getWhere().getGeography().getLocation()) &&
            address.getWhere().getGeography().getLocation().getLat() != 0.0 &&
            address.getWhere().getGeography().getLocation().getLon() != 0.0 &&
            !isUndefined(address.getWhere().getState()) &&
            !isUndefined(address.getWhere().getCity()) &&
            !isUndefined(address.getWhere().getStreet()) &&
            !isUndefined(address.getWhere().getHouseNumber()) &&
            areContactsValid(address.getContacts());
    }

    private boolean areContactsValid(List<PlaceOriginalDTO.Contact> contacts) {
        if (Objects.isNull(contacts) || contacts.isEmpty()) {
            return false;
        }

        contacts.removeIf(contact -> Objects.isNull(contact.getCallLink()));
        return !contacts.isEmpty();
    }

    private boolean isUndefined(String value) {
        return Objects.isNull(value) || value.isBlank();
    }

}
