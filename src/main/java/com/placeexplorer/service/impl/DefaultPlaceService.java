package com.placeexplorer.service.impl;

import com.placeexplorer.comparator.PlaceComparator;
import com.placeexplorer.model.entity.Place;
import com.placeexplorer.repository.PlaceRepository;
import com.placeexplorer.service.PlaceService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DefaultPlaceService implements PlaceService {

    private final Logger logger = LoggerFactory.getLogger(DefaultPlaceService.class);

    @Resource
    private PlaceRepository placeRepository;

    @Resource
    private PlaceComparator defaultPlaceComparator;

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
    public void updateAsynchronouslyIfNeeded(Place place) {
        if (defaultPlaceComparator.shouldUpdate(place, placeRepository.findByUid(place.getUid()))) {
            logger.info("Updating place asynchronously, since new place is different");
            placeRepository.save(place);
        }
    }

    @Override
    public boolean exists(String uid) {
        return placeRepository.existsByUid(uid);
    }

}
