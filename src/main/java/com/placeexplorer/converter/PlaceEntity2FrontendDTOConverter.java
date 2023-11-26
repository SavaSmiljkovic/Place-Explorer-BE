package com.placeexplorer.converter;

import com.placeexplorer.errorHandler.dedicatedException.PlaceConverterException;
import com.placeexplorer.model.dto.PlaceFrontendDTO;
import com.placeexplorer.model.entity.Place;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PlaceEntity2FrontendDTOConverter implements Converter<Place, PlaceFrontendDTO> {

    @Override
    public PlaceFrontendDTO convert(Place source) {

        if (Objects.isNull(source)) {
            throw new PlaceConverterException("Exception while converting Place to PlaceFrontendDTO");
        }

        return new PlaceFrontendDTO(source.getUid(),
                                    source.getType(),
                                    source.getName(),
                                    source.getAddress(),
                                    source.getRatingCount(),
                                    source.getRatingAverage(),
                                    source.getAddresses(),
                                    source.getDays());
    }

}
