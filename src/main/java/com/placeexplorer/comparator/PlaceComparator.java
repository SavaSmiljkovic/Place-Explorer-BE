package com.placeexplorer.comparator;

import com.placeexplorer.model.entity.Place;

public interface PlaceComparator {

    /**
     * Checks if place from database should be updated.
     *
     * @param newPlace Place that is new and that will be compared to the place from database
     * @param oldPlace Place from database that will be compared to the new place
     */
    boolean shouldUpdate(Place newPlace, Place oldPlace);

}
