package com.placeexplorer.model.dto;

import com.placeexplorer.model.Address;
import com.placeexplorer.model.Day;
import com.placeexplorer.model.Shift;
import com.placeexplorer.model.OpeningStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

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
    private OpeningStatus openingStatus;
    private double[] ratingStars;
    private Map<String, List<Shift>> groupedDays;

}
