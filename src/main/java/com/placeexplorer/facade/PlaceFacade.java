package com.placeexplorer.facade;

import com.placeexplorer.model.dto.PlaceFrontendDTO;
import com.placeexplorer.model.dto.PlaceOriginalDTO;

public interface PlaceFacade {

    /**
     * Converts PlaceOriginalDTO to PlaceFrontendDTO without querying database.
     *
     * @param placeOriginalDTO DTO that will be converted to UI DTO form
     */
    PlaceFrontendDTO get(PlaceOriginalDTO placeOriginalDTO);

    /**
     * Gets Place from database by uid.
     *
     * @param uid UID of requested Place
     */
    PlaceFrontendDTO getFromDB(String uid);

    /**
     * Saves Place in database in background asynchronously.
     *
     * @param placeOriginalDTO DTO that will be converted and saved in database
     */
    void saveAsynchronously(PlaceOriginalDTO placeOriginalDTO);

    /**
     * Updates Place in database, if needed, in background asynchronously.
     *
     * @param placeOriginalDTO DTO that will be converted and saved in database
     */
    void updateAsynchronouslyIfNeeded(PlaceOriginalDTO placeOriginalDTO);

    /**
     * Checks if Place exists in database by uid.
     *
     * @param uid UID of requested Place
     */
    boolean existsByUid(String uid);

}
