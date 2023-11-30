package com.placeexplorer.formatter.impl;

import com.placeexplorer.formatter.PlaceFormatter;
import com.placeexplorer.model.Address;
import com.placeexplorer.model.Day;
import com.placeexplorer.model.DayName;
import com.placeexplorer.model.Shift;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Configuration
public class DefaultPlaceFormatter implements PlaceFormatter {

    @Override
    public double[] getRatingStars(double averageRating) {
        double[] stars = {0, 0, 0, 0, 0};

        if (averageRating < 0 || averageRating > 5) {
            return stars;
        }

        int i = 0;
        double starsToFill = Math.floor(averageRating * 2) / 2;

        while (starsToFill > 0.5) {
            stars[i] = 1;
            i++;
            starsToFill -= 1;
        }

        if (starsToFill == 0.5) {
            stars[i] = 0.5;
        }

        return stars;
    }

    @Override
    public List<Address> formatAddress(List<Address> addresses) {
        return addresses.stream()
                        .map(address -> {
                            List<String> formattedPhoneNumbers = address.getPhoneNumbers().stream()
                                                                        .map(this::formatPhoneNumber)
                                                                        .toList();
                            address.setPhoneNumbers(formattedPhoneNumbers);
                            return address;
                        })
                        .toList();
    }

    @Override
    public Map<String, List<Shift>> groupDaysByOpeningHours(List<Day> workingDays) {
        workingDays = workingDays.stream().sorted(Comparator.comparingInt(day -> DayName.valueOf(day.getName().toString()).ordinal())).toList();
        Map<String, List<Shift>> groupedMap = getGroupedDays(workingDays);
        List<String> wantedOrder = Arrays.asList("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday");
        populateGroupedMapWithAllDays(groupedMap, workingDays, wantedOrder);
        return getSortedGroupMap(groupedMap, wantedOrder);
    }

    private Map<String, List<Shift>> getGroupedDays(List<Day> workingDays) {
        Map<String, List<Shift>> groupedMap = new LinkedHashMap<>();

        for (int i = 0; i < workingDays.size(); i++) {
            int lastDayWithSameShiftsIndex = getLastDayWithSameShiftsIndex(i, workingDays);
            if (lastDayWithSameShiftsIndex > i) {

                String dayFrom = capitalize(workingDays.get(i).getName().toString());
                String dayTo = capitalize(workingDays.get(lastDayWithSameShiftsIndex).getName().toString());
                List<Shift> sameShifts = workingDays.get(i).getShifts();

                groupedMap.put(dayFrom + " - " + dayTo, sameShifts);
                i = lastDayWithSameShiftsIndex;

            } else {

                String dayName = capitalize(workingDays.get(i).getName().toString());
                List<Shift> shifts = workingDays.get(i).getShifts();
                groupedMap.put(dayName, shifts);

            }
        }

        return groupedMap;
    }

    private int getLastDayWithSameShiftsIndex(int currentIndex, List<Day> workingDays) {
        if (currentIndex >= workingDays.size()) {
            return currentIndex;
        }

        int lastDayWithSameShiftsIndex = currentIndex;

        for (int i = currentIndex + 1; i < workingDays.size(); i++) {
            if (areShiftsSame(workingDays.get(currentIndex).getShifts(), workingDays.get(i).getShifts())) {
                lastDayWithSameShiftsIndex = i;
            } else {
                return lastDayWithSameShiftsIndex;
            }
        }

        return lastDayWithSameShiftsIndex;
    }

    private boolean areShiftsSame(List<Shift> shifts1, List<Shift> shifts2) {
        if (shifts1.size() != shifts2.size()) {
            return false;
        }

        for (int i = 0; i < shifts1.size(); i++) {
            if (!Objects.equals(shifts1.get(i).getStart(), shifts2.get(i).getStart()) ||
                !Objects.equals(shifts1.get(i).getEnd(), shifts2.get(i).getEnd())) {
                return false;
            }
        }

        return true;
    }

    private void populateGroupedMapWithAllDays(Map<String, List<Shift>> groupedMap, List<Day> workingDays, List<String> wantedOrder) {
        for (String orderedDay : wantedOrder) {
            if (Objects.isNull(workingDays.stream()
                                          .filter(day -> day.getName().toString().toLowerCase().equals(orderedDay))
                                          .findFirst()
                                          .orElse(null))) {

                groupedMap.put(capitalize(orderedDay), null);
            }
        }
    }

    private Map<String, List<Shift>> getSortedGroupMap(Map<String, List<Shift>> groupedMap, List<String> wantedOrder) {
        Map<String, List<Shift>> sortedGroupedMap = new LinkedHashMap<>();

        for (String orderedDay : wantedOrder) {
            String groupedKey = groupedMap.keySet().stream()
                                          .filter(key -> key.toLowerCase().contains(orderedDay))
                                          .findFirst()
                                          .orElse(null);

            if (Objects.nonNull(groupedKey)) {
                sortedGroupedMap.put(groupedKey, groupedMap.get(groupedKey));
                groupedMap.remove(groupedKey);
            }
        }

        return sortedGroupedMap;
    }

    private String formatPhoneNumber(String phoneNumber) {
        StringBuilder formattedPhoneNumber = new StringBuilder();

        if (phoneNumber.startsWith("+")) {
            for (int i = 0; i < phoneNumber.length(); i++) {
                formattedPhoneNumber.append(phoneNumber.charAt(i));
                if (i == 2 || i == 4 || i == 7 || (i == 9 && phoneNumber.length() > 10)) {
                    formattedPhoneNumber.append(' ');
                }
            }
        } else if (phoneNumber.startsWith("0")) {
            for (int i = 0; i < phoneNumber.length(); i++) {
                formattedPhoneNumber.append(phoneNumber.charAt(i));
                if (i == 2 || i == 5 || i == 7 || (i == 9 && phoneNumber.length() > 10)) {
                    formattedPhoneNumber.append(' ');
                }
            }
        } else {
            return phoneNumber;
        }

        return formattedPhoneNumber.toString();
    }

    private String capitalize(String input) {
        return input.toLowerCase().substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

}
