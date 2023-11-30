package com.placeexplorer.converter;

import com.placeexplorer.formatter.PlaceFormatter;
import com.placeexplorer.errorHandler.dedicatedException.PlaceConverterException;
import com.placeexplorer.model.Address;
import com.placeexplorer.model.Day;
import com.placeexplorer.model.Shift;
import com.placeexplorer.model.dto.PlaceFrontendDTO;
import com.placeexplorer.model.dto.PlaceOriginalDTO;
import com.placeexplorer.model.DayName;
import com.placeexplorer.model.dto.helper.OriginalAddress;
import com.placeexplorer.model.dto.helper.OriginalContact;
import com.placeexplorer.model.dto.helper.OriginalLocation;
import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class PlaceOriginalDTO2FrontendDTOConverter implements Converter<PlaceOriginalDTO, PlaceFrontendDTO> {

    @Resource
    private Days2PlaceOpeningStatusConverter days2PlaceOpeningStatusConverter;

    @Resource
    private PlaceFormatter defaultPlaceFormatter;

    @Override
    public PlaceFrontendDTO convert(PlaceOriginalDTO source) {

        if (Objects.isNull(source)) {
            throw new PlaceConverterException("Exception while converting PlaceOriginalDTO to PlaceFrontendDTO");
        }

        List<Day> days = convertDays(source.getOpeningHours().getDays());

        return new PlaceFrontendDTO(source.getLocalEntryId(),
                                    source.getEntryType(),
                                    source.getDisplayedWhat(),
                                    source.getDisplayedWhere(),
                                    source.getPlaceFeedbackSummary().getRatingsCount(),
                                    source.getPlaceFeedbackSummary().getAverageRating(),
                                    defaultPlaceFormatter.formatAddress(convertAddresses(source.getAddresses())),
                                    days,
                                    days2PlaceOpeningStatusConverter.convert(days),
                                    defaultPlaceFormatter.getRatingStars(source.getPlaceFeedbackSummary().getAverageRating()),
                                    defaultPlaceFormatter.groupDaysByOpeningHours(days));
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

    private List<Day> convertDays(Map<String, List<Shift>> daysMap) {
        return daysMap.entrySet().stream()
                      .map(entry -> new Day(DayName.valueOf(entry.getKey().toUpperCase()), entry.getValue()))
                      .toList();
    }


}
