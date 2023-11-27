package com.placeexplorer.model.dto.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OriginalWhere {

    @JsonProperty("house_number")
    private String houseNumber;
    private String street;
    private String city;
    private String state;
    private OriginalGeography geography;

}
