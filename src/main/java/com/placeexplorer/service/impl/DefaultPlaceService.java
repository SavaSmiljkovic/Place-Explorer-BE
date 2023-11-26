package com.placeexplorer.service.impl;

import com.placeexplorer.model.entity.Place;
import com.placeexplorer.repository.PlaceRepository;
import com.placeexplorer.service.PlaceService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DefaultPlaceService implements PlaceService {

    @Resource
    private PlaceRepository placeRepository;

    @Override
    public Place get(String uid) {
        return placeRepository.findByUid(uid);
    }

    @Async
    @Override
    public void saveAsynchronously(Place place) {
        placeRepository.save(place);
    }

    @Override
    public boolean exists(String uid) {
        return placeRepository.existsByUid(uid);
    }
}
