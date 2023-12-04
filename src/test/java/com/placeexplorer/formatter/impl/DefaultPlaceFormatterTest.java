package com.placeexplorer.formatter.impl;

import com.placeexplorer.formatter.PlaceFormatter;
import com.placeexplorer.model.Address;
import com.placeexplorer.model.Day;
import com.placeexplorer.model.DayName;
import com.placeexplorer.model.Shift;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class DefaultPlaceFormatterTest {

    private final PlaceFormatter defaultPlaceFormatter = new DefaultPlaceFormatter();

    @Test
    void groupDaysSortingTest() {
        Shift sameShift = new Shift("00:00", "00:00");
        List<Day> workingDays = Arrays.asList(
            new Day(DayName.WEDNESDAY, List.of(sameShift)),
            new Day(DayName.SUNDAY, List.of(sameShift)),
            new Day(DayName.THURSDAY, List.of(sameShift)),
            new Day(DayName.MONDAY, List.of(sameShift)),
            new Day(DayName.FRIDAY, List.of(sameShift)),
            new Day(DayName.SATURDAY, List.of(sameShift)),
            new Day(DayName.TUESDAY, List.of(sameShift))
        );

        PlaceFormatter defaultPlaceFormatter = new DefaultPlaceFormatter();
        Map<String, List<Shift>> expectedGroupedMap = new LinkedHashMap<>();
        expectedGroupedMap.put("Monday - Sunday", List.of(sameShift));
        Map<String, List<Shift>> resultGroupedMap = defaultPlaceFormatter.groupDaysByOpeningHours(workingDays);

        assertEquals(expectedGroupedMap.keySet().toArray()[0], resultGroupedMap.keySet().toArray()[0]);
        assertEquals(expectedGroupedMap.get("Monday - Sunday").get(0).getStart(), resultGroupedMap.get("Monday - Sunday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Monday - Sunday").get(0).getEnd(), resultGroupedMap.get("Monday - Sunday").get(0).getEnd());
    }

    @Test
    void groupDaysNoSameShiftsTest() {

        Shift mondayShift = new Shift("11:11", "11:11");
        Shift tuesdayShift = new Shift("22:22", "22:22");
        Shift wednesdayShift = new Shift("33:33", "33:33");
        Shift thursdayShift = new Shift("44:44", "44:44");
        Shift fridayShift = new Shift("55:55", "55:55");
        Shift saturdayShift = new Shift("66:66", "66:66");
        Shift sundayShift = new Shift("77:77", "77:77");

        List<Day> workingDays = Arrays.asList(
            new Day(DayName.MONDAY, List.of(mondayShift)),
            new Day(DayName.TUESDAY, List.of(tuesdayShift)),
            new Day(DayName.WEDNESDAY, List.of(wednesdayShift)),
            new Day(DayName.THURSDAY, List.of(thursdayShift)),
            new Day(DayName.FRIDAY, List.of(fridayShift)),
            new Day(DayName.SATURDAY, List.of(saturdayShift)),
            new Day(DayName.SUNDAY, List.of(sundayShift))
        );

        Map<String, List<Shift>> expectedGroupedMap = new LinkedHashMap<>();
        expectedGroupedMap.put("Monday", List.of(mondayShift));
        expectedGroupedMap.put("Tuesday", List.of(tuesdayShift));
        expectedGroupedMap.put("Wednesday", List.of(wednesdayShift));
        expectedGroupedMap.put("Thursday", List.of(thursdayShift));
        expectedGroupedMap.put("Friday", List.of(fridayShift));
        expectedGroupedMap.put("Saturday", List.of(saturdayShift));
        expectedGroupedMap.put("Sunday", List.of(sundayShift));

        PlaceFormatter defaultPlaceFormatter = new DefaultPlaceFormatter();
        Map<String, List<Shift>> resultGroupedMap = defaultPlaceFormatter.groupDaysByOpeningHours(workingDays);

        assertEquals(expectedGroupedMap.keySet().toArray()[0], resultGroupedMap.keySet().toArray()[0]);
        assertEquals(expectedGroupedMap.keySet().toArray()[1], resultGroupedMap.keySet().toArray()[1]);
        assertEquals(expectedGroupedMap.keySet().toArray()[2], resultGroupedMap.keySet().toArray()[2]);
        assertEquals(expectedGroupedMap.keySet().toArray()[3], resultGroupedMap.keySet().toArray()[3]);
        assertEquals(expectedGroupedMap.keySet().toArray()[4], resultGroupedMap.keySet().toArray()[4]);
        assertEquals(expectedGroupedMap.keySet().toArray()[5], resultGroupedMap.keySet().toArray()[5]);
        assertEquals(expectedGroupedMap.keySet().toArray()[6], resultGroupedMap.keySet().toArray()[6]);

        assertEquals(expectedGroupedMap.get("Monday").get(0).getStart(), resultGroupedMap.get("Monday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Monday").get(0).getEnd(), resultGroupedMap.get("Monday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Tuesday").get(0).getStart(), resultGroupedMap.get("Tuesday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Tuesday").get(0).getEnd(), resultGroupedMap.get("Tuesday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Wednesday").get(0).getStart(), resultGroupedMap.get("Wednesday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Wednesday").get(0).getEnd(), resultGroupedMap.get("Wednesday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Thursday").get(0).getStart(), resultGroupedMap.get("Thursday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Thursday").get(0).getEnd(), resultGroupedMap.get("Thursday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Friday").get(0).getStart(), resultGroupedMap.get("Friday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Friday").get(0).getEnd(), resultGroupedMap.get("Friday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Saturday").get(0).getStart(), resultGroupedMap.get("Saturday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Saturday").get(0).getEnd(), resultGroupedMap.get("Saturday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Sunday").get(0).getStart(), resultGroupedMap.get("Sunday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Sunday").get(0).getEnd(), resultGroupedMap.get("Sunday").get(0).getEnd());
    }

    @Test
    void groupDaysNoSameShiftsWithNonWorkingDaysTest() {

        Shift tuesdayShift = new Shift("22:22", "22:22");
        Shift wednesdayShift = new Shift("33:33", "33:33");
        Shift thursdayShift = new Shift("44:44", "44:44");
        Shift fridayShift = new Shift("55:55", "55:55");

        List<Day> workingDays = Arrays.asList(
            new Day(DayName.TUESDAY, List.of(tuesdayShift)),
            new Day(DayName.WEDNESDAY, List.of(wednesdayShift)),
            new Day(DayName.THURSDAY, List.of(thursdayShift)),
            new Day(DayName.FRIDAY, List.of(fridayShift))
        );

        Map<String, List<Shift>> expectedGroupedMap = new LinkedHashMap<>();
        expectedGroupedMap.put("Monday", null);
        expectedGroupedMap.put("Tuesday", List.of(tuesdayShift));
        expectedGroupedMap.put("Wednesday", List.of(wednesdayShift));
        expectedGroupedMap.put("Thursday", List.of(thursdayShift));
        expectedGroupedMap.put("Friday", List.of(fridayShift));
        expectedGroupedMap.put("Saturday", null);
        expectedGroupedMap.put("Sunday", null);

        PlaceFormatter defaultPlaceFormatter = new DefaultPlaceFormatter();
        Map<String, List<Shift>> resultGroupedMap = defaultPlaceFormatter.groupDaysByOpeningHours(workingDays);

        assertEquals(expectedGroupedMap.keySet().toArray()[0], resultGroupedMap.keySet().toArray()[0]);
        assertEquals(expectedGroupedMap.keySet().toArray()[1], resultGroupedMap.keySet().toArray()[1]);
        assertEquals(expectedGroupedMap.keySet().toArray()[2], resultGroupedMap.keySet().toArray()[2]);
        assertEquals(expectedGroupedMap.keySet().toArray()[3], resultGroupedMap.keySet().toArray()[3]);
        assertEquals(expectedGroupedMap.keySet().toArray()[4], resultGroupedMap.keySet().toArray()[4]);
        assertEquals(expectedGroupedMap.keySet().toArray()[5], resultGroupedMap.keySet().toArray()[5]);
        assertEquals(expectedGroupedMap.keySet().toArray()[6], resultGroupedMap.keySet().toArray()[6]);

        assertNull(resultGroupedMap.get("Monday"));
        assertNull(resultGroupedMap.get("Saturday"));
        assertNull(resultGroupedMap.get("Sunday"));

        assertEquals(expectedGroupedMap.get("Tuesday").get(0).getStart(), resultGroupedMap.get("Tuesday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Tuesday").get(0).getEnd(), resultGroupedMap.get("Tuesday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Wednesday").get(0).getStart(), resultGroupedMap.get("Wednesday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Wednesday").get(0).getEnd(), resultGroupedMap.get("Wednesday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Thursday").get(0).getStart(), resultGroupedMap.get("Thursday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Thursday").get(0).getEnd(), resultGroupedMap.get("Thursday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Friday").get(0).getStart(), resultGroupedMap.get("Friday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Friday").get(0).getEnd(), resultGroupedMap.get("Friday").get(0).getEnd());
    }

    @Test
    void groupDaysMondayToTuesday() {

        Shift mondayShift = new Shift("11:11", "11:11");
        Shift wednesdayShift = new Shift("33:33", "33:33");
        Shift thursdayShift = new Shift("44:44", "44:44");
        Shift fridayShift = new Shift("55:55", "55:55");
        Shift saturdayShift = new Shift("66:66", "66:66");
        Shift sundayShift = new Shift("77:77", "77:77");

        List<Day> workingDays = Arrays.asList(
            new Day(DayName.MONDAY, List.of(mondayShift)),
            new Day(DayName.TUESDAY, List.of(mondayShift)),
            new Day(DayName.WEDNESDAY, List.of(wednesdayShift)),
            new Day(DayName.THURSDAY, List.of(thursdayShift)),
            new Day(DayName.FRIDAY, List.of(fridayShift)),
            new Day(DayName.SATURDAY, List.of(saturdayShift)),
            new Day(DayName.SUNDAY, List.of(sundayShift))
        );

        Map<String, List<Shift>> expectedGroupedMap = new LinkedHashMap<>();
        expectedGroupedMap.put("Monday - Tuesday", List.of(mondayShift));
        expectedGroupedMap.put("Wednesday", List.of(wednesdayShift));
        expectedGroupedMap.put("Thursday", List.of(thursdayShift));
        expectedGroupedMap.put("Friday", List.of(fridayShift));
        expectedGroupedMap.put("Saturday", List.of(saturdayShift));
        expectedGroupedMap.put("Sunday", List.of(sundayShift));

        PlaceFormatter defaultPlaceFormatter = new DefaultPlaceFormatter();
        Map<String, List<Shift>> resultGroupedMap = defaultPlaceFormatter.groupDaysByOpeningHours(workingDays);

        assertEquals(expectedGroupedMap.keySet().toArray()[0], resultGroupedMap.keySet().toArray()[0]);
        assertEquals(expectedGroupedMap.keySet().toArray()[1], resultGroupedMap.keySet().toArray()[1]);
        assertEquals(expectedGroupedMap.keySet().toArray()[2], resultGroupedMap.keySet().toArray()[2]);
        assertEquals(expectedGroupedMap.keySet().toArray()[3], resultGroupedMap.keySet().toArray()[3]);
        assertEquals(expectedGroupedMap.keySet().toArray()[4], resultGroupedMap.keySet().toArray()[4]);
        assertEquals(expectedGroupedMap.keySet().toArray()[5], resultGroupedMap.keySet().toArray()[5]);

        assertEquals(expectedGroupedMap.get("Monday - Tuesday").get(0).getStart(), resultGroupedMap.get("Monday - Tuesday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Monday - Tuesday").get(0).getEnd(), resultGroupedMap.get("Monday - Tuesday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Wednesday").get(0).getStart(), resultGroupedMap.get("Wednesday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Wednesday").get(0).getEnd(), resultGroupedMap.get("Wednesday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Thursday").get(0).getStart(), resultGroupedMap.get("Thursday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Thursday").get(0).getEnd(), resultGroupedMap.get("Thursday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Friday").get(0).getStart(), resultGroupedMap.get("Friday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Friday").get(0).getEnd(), resultGroupedMap.get("Friday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Saturday").get(0).getStart(), resultGroupedMap.get("Saturday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Saturday").get(0).getEnd(), resultGroupedMap.get("Saturday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Sunday").get(0).getStart(), resultGroupedMap.get("Sunday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Sunday").get(0).getEnd(), resultGroupedMap.get("Sunday").get(0).getEnd());
    }

    @Test
    void groupDaysTuesdayToThursdayAndFridayToSaturdayTest() {

        Shift mondayShift = new Shift("11:11", "11:11");
        Shift tuesdayShift = new Shift("22:22", "22:22");
        Shift fridayShift = new Shift("55:55", "55:55");
        Shift sundayShift = new Shift("77:77", "77:77");

        List<Day> workingDays = Arrays.asList(
            new Day(DayName.MONDAY, List.of(mondayShift)),
            new Day(DayName.TUESDAY, List.of(tuesdayShift)),
            new Day(DayName.WEDNESDAY, List.of(tuesdayShift)),
            new Day(DayName.THURSDAY, List.of(tuesdayShift)),
            new Day(DayName.FRIDAY, List.of(fridayShift)),
            new Day(DayName.SATURDAY, List.of(fridayShift)),
            new Day(DayName.SUNDAY, List.of(sundayShift))
        );

        Map<String, List<Shift>> expectedGroupedMap = new LinkedHashMap<>();
        expectedGroupedMap.put("Monday", List.of(mondayShift));
        expectedGroupedMap.put("Tuesday - Thursday", List.of(tuesdayShift));
        expectedGroupedMap.put("Friday - Saturday", List.of(fridayShift));
        expectedGroupedMap.put("Sunday", List.of(sundayShift));

        PlaceFormatter defaultPlaceFormatter = new DefaultPlaceFormatter();
        Map<String, List<Shift>> resultGroupedMap = defaultPlaceFormatter.groupDaysByOpeningHours(workingDays);

        assertEquals(expectedGroupedMap.keySet().toArray()[0], resultGroupedMap.keySet().toArray()[0]);
        assertEquals(expectedGroupedMap.keySet().toArray()[1], resultGroupedMap.keySet().toArray()[1]);
        assertEquals(expectedGroupedMap.keySet().toArray()[2], resultGroupedMap.keySet().toArray()[2]);
        assertEquals(expectedGroupedMap.keySet().toArray()[3], resultGroupedMap.keySet().toArray()[3]);

        assertEquals(expectedGroupedMap.get("Monday").get(0).getStart(), resultGroupedMap.get("Monday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Monday").get(0).getEnd(), resultGroupedMap.get("Monday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Tuesday - Thursday").get(0).getStart(), resultGroupedMap.get("Tuesday - Thursday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Tuesday - Thursday").get(0).getEnd(), resultGroupedMap.get("Tuesday - Thursday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Friday - Saturday").get(0).getStart(), resultGroupedMap.get("Friday - Saturday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Friday - Saturday").get(0).getEnd(), resultGroupedMap.get("Friday - Saturday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Sunday").get(0).getStart(), resultGroupedMap.get("Sunday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Sunday").get(0).getEnd(), resultGroupedMap.get("Sunday").get(0).getEnd());
    }

    @Test
    void groupDaysTuesdayToThursdayAndFridayToSaturdayWithNonWorkingDaysTest() {

        Shift tuesdayShift = new Shift("22:22", "22:22");
        Shift fridayShift = new Shift("55:55", "55:55");

        List<Day> workingDays = Arrays.asList(
            new Day(DayName.TUESDAY, List.of(tuesdayShift)),
            new Day(DayName.WEDNESDAY, List.of(tuesdayShift)),
            new Day(DayName.THURSDAY, List.of(tuesdayShift)),
            new Day(DayName.FRIDAY, List.of(fridayShift)),
            new Day(DayName.SATURDAY, List.of(fridayShift))
        );

        Map<String, List<Shift>> expectedGroupedMap = new LinkedHashMap<>();
        expectedGroupedMap.put("Monday", null);
        expectedGroupedMap.put("Tuesday - Thursday", List.of(tuesdayShift));
        expectedGroupedMap.put("Friday - Saturday", List.of(fridayShift));
        expectedGroupedMap.put("Sunday", null);

        PlaceFormatter defaultPlaceFormatter = new DefaultPlaceFormatter();
        Map<String, List<Shift>> resultGroupedMap = defaultPlaceFormatter.groupDaysByOpeningHours(workingDays);

        assertEquals(expectedGroupedMap.keySet().toArray()[0], resultGroupedMap.keySet().toArray()[0]);
        assertEquals(expectedGroupedMap.keySet().toArray()[1], resultGroupedMap.keySet().toArray()[1]);
        assertEquals(expectedGroupedMap.keySet().toArray()[2], resultGroupedMap.keySet().toArray()[2]);
        assertEquals(expectedGroupedMap.keySet().toArray()[3], resultGroupedMap.keySet().toArray()[3]);

        assertNull(resultGroupedMap.get("Monday"));
        assertNull(resultGroupedMap.get("Sunday"));

        assertEquals(expectedGroupedMap.get("Tuesday - Thursday").get(0).getStart(), resultGroupedMap.get("Tuesday - Thursday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Tuesday - Thursday").get(0).getEnd(), resultGroupedMap.get("Tuesday - Thursday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Friday - Saturday").get(0).getStart(), resultGroupedMap.get("Friday - Saturday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Friday - Saturday").get(0).getEnd(), resultGroupedMap.get("Friday - Saturday").get(0).getEnd());
    }

    @Test
    void groupDaysSaturdayToSundayTest() {

        Shift mondayShift = new Shift("11:11", "11:11");
        Shift tuesdayShift = new Shift("22:22", "22:22");
        Shift wednesdayShift = new Shift("33:33", "33:33");
        Shift thursdayShift = new Shift("44:44", "44:44");
        Shift fridayShift = new Shift("55:55", "55:55");
        Shift saturdayShift = new Shift("66:66", "66:66");

        List<Day> workingDays = Arrays.asList(
            new Day(DayName.MONDAY, List.of(mondayShift)),
            new Day(DayName.TUESDAY, List.of(tuesdayShift)),
            new Day(DayName.WEDNESDAY, List.of(wednesdayShift)),
            new Day(DayName.THURSDAY, List.of(thursdayShift)),
            new Day(DayName.FRIDAY, List.of(fridayShift)),
            new Day(DayName.SATURDAY, List.of(saturdayShift)),
            new Day(DayName.SUNDAY, List.of(saturdayShift))
        );

        Map<String, List<Shift>> expectedGroupedMap = new LinkedHashMap<>();
        expectedGroupedMap.put("Monday", List.of(mondayShift));
        expectedGroupedMap.put("Tuesday", List.of(tuesdayShift));
        expectedGroupedMap.put("Wednesday", List.of(wednesdayShift));
        expectedGroupedMap.put("Thursday", List.of(thursdayShift));
        expectedGroupedMap.put("Friday", List.of(fridayShift));
        expectedGroupedMap.put("Saturday - Sunday", List.of(saturdayShift));

        PlaceFormatter defaultPlaceFormatter = new DefaultPlaceFormatter();
        Map<String, List<Shift>> resultGroupedMap = defaultPlaceFormatter.groupDaysByOpeningHours(workingDays);

        assertEquals(expectedGroupedMap.keySet().toArray()[0], resultGroupedMap.keySet().toArray()[0]);
        assertEquals(expectedGroupedMap.keySet().toArray()[1], resultGroupedMap.keySet().toArray()[1]);
        assertEquals(expectedGroupedMap.keySet().toArray()[2], resultGroupedMap.keySet().toArray()[2]);
        assertEquals(expectedGroupedMap.keySet().toArray()[3], resultGroupedMap.keySet().toArray()[3]);
        assertEquals(expectedGroupedMap.keySet().toArray()[4], resultGroupedMap.keySet().toArray()[4]);
        assertEquals(expectedGroupedMap.keySet().toArray()[5], resultGroupedMap.keySet().toArray()[5]);

        assertEquals(expectedGroupedMap.get("Monday").get(0).getStart(), resultGroupedMap.get("Monday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Monday").get(0).getEnd(), resultGroupedMap.get("Monday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Tuesday").get(0).getStart(), resultGroupedMap.get("Tuesday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Tuesday").get(0).getEnd(), resultGroupedMap.get("Tuesday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Wednesday").get(0).getStart(), resultGroupedMap.get("Wednesday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Wednesday").get(0).getEnd(), resultGroupedMap.get("Wednesday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Thursday").get(0).getStart(), resultGroupedMap.get("Thursday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Thursday").get(0).getEnd(), resultGroupedMap.get("Thursday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Friday").get(0).getStart(), resultGroupedMap.get("Friday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Friday").get(0).getEnd(), resultGroupedMap.get("Friday").get(0).getEnd());

        assertEquals(expectedGroupedMap.get("Saturday - Sunday").get(0).getStart(), resultGroupedMap.get("Saturday - Sunday").get(0).getStart());
        assertEquals(expectedGroupedMap.get("Saturday - Sunday").get(0).getEnd(), resultGroupedMap.get("Saturday - Sunday").get(0).getEnd());
    }

    @Test
    void getRatingStars() {
        double averageRating = 0;
        double[] expectedStars = new double[]{0, 0, 0, 0, 0};
        double[] resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 0.4;
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 0.5;
        expectedStars = new double[]{0.5, 0, 0, 0, 0};
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 0.9;
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 1;
        expectedStars = new double[]{1, 0, 0, 0, 0};
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 1.4;
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 1.5;
        expectedStars = new double[]{1, 0.5, 0, 0, 0};
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 1.9;
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 2;
        expectedStars = new double[]{1, 1, 0, 0, 0};
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 2.4;
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 2.5;
        expectedStars = new double[]{1, 1, 0.5, 0, 0};
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 2.9;
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 3;
        expectedStars = new double[]{1, 1, 1, 0, 0};
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 3.4;
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 3.5;
        expectedStars = new double[]{1, 1, 1, 0.5, 0};
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 3.9;
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 4;
        expectedStars = new double[]{1, 1, 1, 1, 0};
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 4.4;
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 4.5;
        expectedStars = new double[]{1, 1, 1, 1, 0.5};
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 4.9;
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));

        averageRating = 5;
        expectedStars = new double[]{1, 1, 1, 1, 1};
        resultStars = defaultPlaceFormatter.getRatingStars(averageRating);
        assertEquals(Arrays.toString(expectedStars), Arrays.toString(resultStars));
    }

    @Test
    void formatAddress() {
        final String state = "STATE";
        final String city = "CITY";
        final String street = "STREET";
        final String houseNumber = "HOUSE_NUMBER";
        final double latitude = 1.1;
        final double longitude = 1.1;

        final String phoneToTest1 = "+41791234567";
        final String phoneExpect1 = "+41 79 123 45 67";

        final String phoneToTest2 = "0223456789";
        final String phoneExpect2 = "022 345 67 89";

        final String phoneToTest3 = "0800123456";
        final String phoneExpect3 = "080 012 34 56";

        List<String> phoneNumbersExpect = Arrays.asList(phoneExpect1, phoneExpect2, phoneExpect3);
        List<String> phoneNumbersToTest = Arrays.asList(phoneToTest1, phoneToTest2, phoneToTest3);

        List<Address> addressesExpect = Collections.singletonList(new Address(state, city, street, houseNumber, phoneNumbersExpect, latitude, longitude));
        List<Address> addressesToTest =
            defaultPlaceFormatter.formatAddress(Collections.singletonList(new Address(state,
                                                                                      city,
                                                                                      street,
                                                                                      houseNumber,
                                                                                      phoneNumbersToTest,
                                                                                      latitude,
                                                                                      longitude)));

        assertEquals(addressesExpect.get(0).getPhoneNumbers().get(0), addressesToTest.get(0).getPhoneNumbers().get(0));
        assertEquals(addressesExpect.get(0).getPhoneNumbers().get(1), addressesToTest.get(0).getPhoneNumbers().get(1));
        assertEquals(addressesExpect.get(0).getPhoneNumbers().get(2), addressesToTest.get(0).getPhoneNumbers().get(2));
    }
}

