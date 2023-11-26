package com.placeexplorer.model.entity;

import com.mongodb.lang.NonNull;
import com.placeexplorer.model.Day;
import com.placeexplorer.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Document("place")
public class Place {

    @Id
    private String uid;

    @NonNull
    private String type;

    @NonNull
    private String name;

    @NonNull
    private String address;

    @NonNull
    private int ratingCount;

    @NonNull
    private double ratingAverage;

    @NonNull
    private List<Address> addresses;

    @NonNull
    private List<Day> days;

}
