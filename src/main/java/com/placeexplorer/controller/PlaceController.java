package com.placeexplorer.controller;

import com.placeexplorer.errorHandler.ErrorHandler;
import com.placeexplorer.errorHandler.ErrorType;
import com.placeexplorer.facade.PlaceFacade;
import com.placeexplorer.model.dto.PlaceFrontendDTO;
import com.placeexplorer.model.dto.PlaceOriginalDTO;
import com.placeexplorer.validator.PlaceOriginalDTOValidator;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("${places.root}")
public class PlaceController {

    private final Logger logger = LoggerFactory.getLogger(PlaceController.class);

    @Value("${api.rest.places}")
    private String placesAPI;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private ErrorHandler defaultErrorHandler;

    @Resource
    private PlaceOriginalDTOValidator placeOriginalDTOValidator;

    @Resource
    private PlaceFacade defaultPlaceFacade;

    @GetMapping("/single")
    public ResponseEntity<PlaceFrontendDTO> getPlaceByUid(@RequestParam String uid) {
        try {
            PlaceOriginalDTO placeOriginalDTO = restTemplate.getForObject(placesAPI + uid, PlaceOriginalDTO.class);

            if (Objects.isNull(placeOriginalDTO)) {
                return defaultErrorHandler.handle(ErrorType.ORIGINAL_PLACE_NULL);
            }

            if (!placeOriginalDTOValidator.isValid(placeOriginalDTO)) {
                return defaultErrorHandler.handle(ErrorType.ORIGINAL_PLACE_INVALID);
            }

            if (!defaultPlaceFacade.existsByUid(uid)) {
                logger.info("Saving original placeDTO asynchronously in DB. UID: {}", uid);
                defaultPlaceFacade.saveAsynchronously(placeOriginalDTO);
            }

            logger.info("Returning original placeDTO");
            return new ResponseEntity<>(defaultPlaceFacade.get(placeOriginalDTO), HttpStatus.OK);

        } catch (RestClientException e) {

            if (!defaultPlaceFacade.existsByUid(uid)) {
                return defaultErrorHandler.handle(ErrorType.UID_UNRECOGNIZED);
            }

            logger.info("Returning placeDTO from DB, since RestTemplate threw Exception");
            return new ResponseEntity<>(defaultPlaceFacade.getFromDB(uid), HttpStatus.OK);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlaceFrontendDTO>> getAllPlaces() {

        List<String> allUids = Arrays.asList("GXvPAor1ifNfpF0U5PTG0w", "ohGSnJtMIC5nPfYRi_HTAg");
        List<PlaceFrontendDTO> placeFrontendDTOs = new ArrayList<>();

        for (String uid : allUids) {
            try {
                PlaceOriginalDTO placeOriginalDTO = restTemplate.getForObject(placesAPI + uid, PlaceOriginalDTO.class);

                if (Objects.isNull(placeOriginalDTO) || !placeOriginalDTOValidator.isValid(placeOriginalDTO)) {
                    logger.error("Skipping original PlaceDTO with UID: {}", uid);
                    continue;
                }

                if (!defaultPlaceFacade.existsByUid(uid)) {
                    logger.info("Saving original placeDTO asynchronously in DB. UID: {}", uid);
                    defaultPlaceFacade.saveAsynchronously(placeOriginalDTO);
                }

                logger.info("Adding original placeDTO to response");
                placeFrontendDTOs.add(defaultPlaceFacade.get(placeOriginalDTO));

            } catch (RestClientException e) {

                if (!defaultPlaceFacade.existsByUid(uid)) {
                    logger.error("UID is not recognized in DB, skipping PlaceDTO with UID: {}", uid);
                    continue;
                }

                logger.info("Adding placeDTO from DB, since RestTemplate threw Exception");
                placeFrontendDTOs.add(defaultPlaceFacade.getFromDB(uid));
            }
        }

        logger.info("Returning {}/{} placeDTOs", placeFrontendDTOs.size(), allUids.size());
        return new ResponseEntity<>(placeFrontendDTOs, HttpStatus.OK);
    }

}
