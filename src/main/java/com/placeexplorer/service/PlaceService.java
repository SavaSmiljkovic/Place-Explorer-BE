package com.placeexplorer.service;

import com.placeexplorer.model.entity.Place;

public interface PlaceService {

    /**
     * Gets Place from database by uid.
     *
     * @param uid UID of requested Place
     */
    Place get(String uid);

    /**
     * Saves Place in database in background asynchronously.
     *
     * @param place Place that will be saved in database
     */
    void saveAsynchronously(Place place);

    /**
     * Checks if Place exists in database by uid.
     *
     * @param uid UID of requested Place
     */
    boolean exists(String uid);
}
