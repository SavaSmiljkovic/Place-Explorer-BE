package com.placeexplorer.formatter;

import com.placeexplorer.model.Address;
import com.placeexplorer.model.Day;
import com.placeexplorer.model.Shift;

import java.util.List;
import java.util.Map;

public interface PlaceFormatter {

    /**
     * Returns array of 5 numbers, where each number represents percentage of a start that needs to be shown on UI (0 | 0.5 | 1).
     *
     * @param averageRating Average Rating of a Place
     */
    double[] getRatingStars(double averageRating);

    /**
     * Formats phone numbers inside of addresses and returns addresses with formatted phone numbers.
     *
     * @param addresses List of addresses that Place is located on
     */
    List<Address> formatAddress(List<Address> addresses);

    /**
     * Groups working days with the same opening hours.
     *
     * @param workingDays List of days when place is working
     */
    Map<String, List<Shift>> groupDaysByOpeningHours(List<Day> workingDays);

}
