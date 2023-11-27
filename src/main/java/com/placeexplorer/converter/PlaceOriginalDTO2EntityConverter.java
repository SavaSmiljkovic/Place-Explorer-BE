package com.placeexplorer.converter;

import com.placeexplorer.errorHandler.dedicatedException.PlaceConverterException;
import com.placeexplorer.model.Address;
import com.placeexplorer.model.Day;
import com.placeexplorer.model.Shift;
import com.placeexplorer.model.dto.PlaceOriginalDTO;
import com.placeexplorer.model.DayName;
import com.placeexplorer.model.dto.helper.OriginalAddress;
import com.placeexplorer.model.dto.helper.OriginalContact;
import com.placeexplorer.model.dto.helper.OriginalLocation;
import com.placeexplorer.model.entity.Place;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class PlaceOriginalDTO2EntityConverter implements Converter<PlaceOriginalDTO, Place> {

    @Override
    public Place convert(PlaceOriginalDTO source) {

        if (Objects.isNull(source)) {
            throw new PlaceConverterException("Exception while converting PlaceOriginalDTO to Place");
        }

        return new Place(source.getLocalEntryId(),
                         source.getEntryType(),
                         source.getDisplayedWhat(),
                         source.getDisplayedWhere(),
                         source.getPlaceFeedbackSummary().getRatingsCount(),
                         source.getPlaceFeedbackSummary().getAverageRating(),
                         convertAddresses(source.getAddresses()),
                         convertOpeningHours(source.getOpeningHours().getDays()));
    }

    private List<Address> convertAddresses(List<OriginalAddress> originalAddresses) {
        return originalAddresses.stream()
                                .map(this::convertAddress)
                                .toList();
    }

    private Address convertAddress(OriginalAddress originalAddress) {
        OriginalLocation location = originalAddress.getWhere().getGeography().getLocation();
        List<String> phoneNumbers = originalAddress.getContacts().stream()
                                                   .map(OriginalContact::getCallLink)
                                                   .filter(Objects::nonNull)
                                                   .toList();

        return new Address(originalAddress.getWhere().getState(),
                           originalAddress.getWhere().getCity(),
                           originalAddress.getWhere().getStreet(),
                           originalAddress.getWhere().getHouseNumber(),
                           phoneNumbers,
                           location.getLat(),
                           location.getLon()
        );
    }

    private List<Day> convertOpeningHours(Map<String, List<Shift>> daysMap) {
        return daysMap.entrySet().stream()
                      .map(entry -> new Day(DayName.valueOf(entry.getKey().toUpperCase()), entry.getValue()))
                      .toList();
    }

}
