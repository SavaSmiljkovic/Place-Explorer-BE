package com.placeexplorer.model.dto;

import com.placeexplorer.model.Address;
import com.placeexplorer.model.Day;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PlaceFrontendDTO {

    private String uid;
    private String type;
    private String name;
    private String address;
    private int ratingCount;
    private double ratingAverage;
    private List<Address> addresses;
    private List<Day> days;

}
