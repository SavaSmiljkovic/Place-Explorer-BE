package com.placeexplorer.validator.impl;

import com.placeexplorer.model.dto.PlaceOriginalDTO;
import com.placeexplorer.model.dto.helper.OriginalAddress;
import com.placeexplorer.model.dto.helper.OriginalContact;
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

        for (OriginalAddress address : placeOriginalDTO.getAddresses()) {
            if (!isValidAddress(address)) {
                logger.error("Invalid address fields");
                return false;
            }
        }

        return true;
    }

    private boolean isValidAddress(OriginalAddress address) {
        return Objects.nonNull(address.getWhere()) &&
            Objects.nonNull(address.getWhere().getGeography()) &&
            Objects.nonNull(address.getWhere().getGeography().getLocation()) &&
            address.getWhere().getGeography().getLocation().getLat() != 0.0 &&
            address.getWhere().getGeography().getLocation().getLon() != 0.0 &&
            isDefined(address.getWhere().getState()) &&
            isDefined(address.getWhere().getCity()) &&
            isDefined(address.getWhere().getStreet()) &&
            isDefined(address.getWhere().getHouseNumber()) &&
            areContactsValid(address.getContacts());
    }

    private boolean areContactsValid(List<OriginalContact> contacts) {
        if (Objects.isNull(contacts) || contacts.isEmpty()) {
            return false;
        }

        contacts.removeIf(contact -> Objects.isNull(contact.getCallLink()));
        return !contacts.isEmpty();
    }

    private boolean isDefined(String value) {
        return Objects.nonNull(value) && !value.isBlank();
    }

}
