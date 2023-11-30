package com.placeexplorer.converter;

import com.placeexplorer.model.Day;
import com.placeexplorer.model.Shift;
import com.placeexplorer.model.OpeningStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Component
public class Days2PlaceOpeningStatusConverter implements Converter<List<Day>, OpeningStatus> {

    private Date currentDate;
    private Date currentTime;
    private String currentDay;
    private static final String BASE_TIME = "2000-01-01T";
    private static final String NEXT_DAY_BASE_TIME = "2000-01-02T";

    @Override
    public OpeningStatus convert(List<Day> workingDays) {

        if (Objects.isNull(currentDate)) {
            currentDate = new Date();
        }

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.US);

        String currentHour = timeFormat.format(currentDate);
        String currentMinute = timeFormat.format(currentDate);
        currentDay = dayFormat.format(currentDate).toUpperCase();
        currentTime = parseTime(BASE_TIME + currentHour + ':' + currentMinute);

        Day workingDay = workingDays.stream()
                                    .filter(day -> day.getName().toString().equals(currentDay))
                                    .findFirst()
                                    .orElse(null);

        if (Objects.nonNull(workingDay)) {

            // Check if there is an active shift
            Shift currentShift = findActiveShiftNow(workingDay);
            if (Objects.nonNull(currentShift)) {
                return new OpeningStatus(true, currentShift.getEnd());
            } else {

                // Find the next shift today
                Shift nextShiftToday = findNextShiftToday(workingDay);
                if (Objects.nonNull(nextShiftToday)) {
                    return new OpeningStatus(false, nextShiftToday.getStart());
                } else {

                    // No more shifts today, find the next working day
                    return getNextWorkingDayOpeningStatus(workingDays);
                }
            }
        } else {

            // Place is not working today, find the next working day
            return getNextWorkingDayOpeningStatus(workingDays);
        }
    }

    private Shift findActiveShiftNow(Day workingDay) {
        return workingDay.getShifts().stream()
                         .filter(shift -> {

                             Date startTime = parseTime(BASE_TIME + shift.getStart());
                             Date endTime = parseTime(shift.getEnd().equals("00:00") ? NEXT_DAY_BASE_TIME + shift.getEnd() : BASE_TIME + shift.getEnd());
                             return currentTime.compareTo(startTime) >= 0 && currentTime.before(endTime);

                         })
                         .findFirst()
                         .orElse(null);
    }

    private Shift findNextShiftToday(Day workingDay) {
        return workingDay.getShifts().stream()
                         .filter(shift -> {
                             Date shiftStart = parseTime(BASE_TIME + shift.getStart());
                             return Objects.nonNull(shiftStart) && shiftStart.after(currentTime);
                         })
                         .findFirst()
                         .orElse(null);
    }

    private OpeningStatus getNextWorkingDayOpeningStatus(List<Day> workingDays) {

        List<String> daysOfWeek = new ArrayList<>(Arrays.asList("sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"));
        int indexOfToday = daysOfWeek.indexOf(currentDay.toLowerCase());

        List<String> nextWorkingDays = new ArrayList<>(daysOfWeek.subList(indexOfToday, daysOfWeek.size()));
        nextWorkingDays.addAll(daysOfWeek.subList(0, indexOfToday));
        nextWorkingDays.remove(0);

        Day nextWorkingDay = nextWorkingDays.stream()
                                            .flatMap(questionableDay -> workingDays.stream()
                                                                                   .filter(workingDay -> workingDay.getName()
                                                                                                                   .toString()
                                                                                                                   .equalsIgnoreCase(questionableDay)))
                                            .findFirst()
                                            .orElse(workingDays.get(0));

        Shift nextWorkingShift = nextWorkingDay.getShifts().get(0);
        return new OpeningStatus(false, nextWorkingShift.getStart() + " " + capitalize(nextWorkingDay.getName().toString().toLowerCase()));
    }

    private Date parseTime(String timeString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String capitalize(String input) {
        return input.toLowerCase().substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

}
