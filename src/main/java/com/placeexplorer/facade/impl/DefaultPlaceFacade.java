package com.placeexplorer.facade.impl;

import com.placeexplorer.converter.PlaceEntity2FrontendDTOConverter;
import com.placeexplorer.converter.PlaceOriginalDTO2EntityConverter;
import com.placeexplorer.converter.PlaceOriginalDTO2FrontendDTOConverter;
import com.placeexplorer.facade.PlaceFacade;
import com.placeexplorer.model.dto.PlaceFrontendDTO;
import com.placeexplorer.model.dto.PlaceOriginalDTO;
import com.placeexplorer.service.PlaceService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DefaultPlaceFacade implements PlaceFacade {

    @Resource
    private PlaceService defaultPlaceService;

    @Resource
    private PlaceEntity2FrontendDTOConverter placeEntity2FrontendDTOConverter;

    @Resource
    private PlaceOriginalDTO2EntityConverter placeOriginalDTO2EntityConverter;

    @Resource
    private PlaceOriginalDTO2FrontendDTOConverter placeOriginalDTO2FrontendDTOConverter;

    @Override
    public PlaceFrontendDTO get(PlaceOriginalDTO placeOriginalDTO) {
        return placeOriginalDTO2FrontendDTOConverter.convert(placeOriginalDTO);
    }

    @Override
    public PlaceFrontendDTO getFromDB(String uid) {
        return placeEntity2FrontendDTOConverter.convert(defaultPlaceService.get(uid));
    }

    @Override
    public void saveAsynchronously(PlaceOriginalDTO placeOriginalDTO) {
        defaultPlaceService.saveAsynchronously(placeOriginalDTO2EntityConverter.convert(placeOriginalDTO));
    }

    @Override
    public boolean existsByUid(String uid) {
        return defaultPlaceService.exists(uid);
    }
}
