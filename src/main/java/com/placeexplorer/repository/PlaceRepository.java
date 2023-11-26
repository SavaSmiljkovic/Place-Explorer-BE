package com.placeexplorer.repository;

import com.placeexplorer.model.entity.Place;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends MongoRepository<Place, String> {

    /**
     * Queries database by uid of Place, and returns that Place.
     *
     * @param uid UID of requested Place
     */
    Place findByUid(String uid);

    /**
     * Queries database to check if Place exists by uid.
     *
     * @param uid UID of requested Place
     */
    boolean existsByUid(String uid);

}
