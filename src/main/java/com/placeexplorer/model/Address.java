package com.placeexplorer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Address {

    private String state;
    private String city;
    private String street;
    private String houseNumber;
    private List<String> phoneNumbers;
    private double latitude;
    private double longitude;

}
