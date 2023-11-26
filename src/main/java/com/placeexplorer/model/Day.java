package com.placeexplorer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Day {

    private DayName name;
    private List<Shift> shifts;

}
