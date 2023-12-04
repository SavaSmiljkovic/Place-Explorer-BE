package com.placeexplorer.converter;

import com.placeexplorer.model.Day;
import com.placeexplorer.model.OpeningStatus;
import com.placeexplorer.model.Shift;
import lombok.Setter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@Setter
public class Days2PlaceOpeningStatusConverter implements Converter<List<Day>, OpeningStatus> {

    @Override
    public OpeningStatus convert(List<Day> source) {

        LocalDateTime currentDateTime = getCurrentTime();

        Day workingDay = source.stream()
                               .filter(day -> day.getName().toString().equals(currentDateTime.getDayOfWeek().toString()))
                               .findFirst()
                               .orElse(null);

        if (workingDay != null) {
            Shift activeShift = getActiveShift(workingDay, currentDateTime.toLocalTime());
            if (activeShift != null) {
                return new OpeningStatus(true, activeShift.getEnd());
            }

            Shift nextShiftToday = getNextShiftToday(workingDay, currentDateTime.toLocalTime());
            if (nextShiftToday != null) {
                return new OpeningStatus(false, nextShiftToday.getStart());
            }
        }

        Day nextWorkingDay = getNextWorkingDay(source, currentDateTime);
        return new OpeningStatus(false, nextWorkingDay.getShifts().get(0).getStart() + " " + capitalize(nextWorkingDay.getName().toString()));
    }

    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

    private Shift getActiveShift(Day workingDay, LocalTime currentTime) {
        return workingDay.getShifts().stream()
                         .filter(shift -> {

                             LocalTime shiftStartTime = LocalTime.parse(shift.getStart());
                             LocalTime shiftEndTime = LocalTime.parse(shift.getEnd());

                             return !currentTime.isBefore(shiftStartTime) &&
                                 currentTime.isBefore(shiftEndTime);

                         })
                         .findFirst()
                         .orElse(null);
    }

    private Shift getNextShiftToday(Day workingDay, LocalTime currentTime) {
        return workingDay.getShifts().stream()
                         .filter(shift -> {

                             LocalTime shiftStartTime = LocalTime.parse(shift.getStart());
                             return currentTime.isBefore(shiftStartTime);

                         })
                         .findFirst()
                         .orElse(null);
    }

    private Day getNextWorkingDay(List<Day> workingDays, LocalDateTime currentDateTime) {
        for (int i = 1; i < 7; i++) {
            String nextDay = currentDateTime.plusDays(i).getDayOfWeek().toString();
            Day nextWorkingDay = workingDays.stream()
                                            .filter(workingDay -> workingDay.getName().toString().equals(nextDay))
                                            .findFirst()
                                            .orElse(null);
            if (nextWorkingDay != null) {
                return nextWorkingDay;
            }
        }
        return workingDays.get(0);
    }

    private String capitalize(String input) {
        return input.toLowerCase().substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

}
