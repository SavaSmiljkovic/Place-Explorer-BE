package com.placeexplorer.converter;

import com.placeexplorer.model.Day;
import com.placeexplorer.model.DayName;
import com.placeexplorer.model.Shift;
import com.placeexplorer.model.OpeningStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Days2OpeningStatusConverterTest {

    private Days2PlaceOpeningStatusConverter converter = new Days2PlaceOpeningStatusConverter();
    private final List<Day> workingDays = getWorkingDays();
    private OpeningStatus result;
    private OpeningStatus expected;

    @Test
    void beforeFirstShift() {
        converter = new Days2PlaceOpeningStatusConverter();
        converter.setCurrentDate(getDate(DayName.MONDAY, 8, 0));

        result = converter.convert(workingDays);
        expected = new OpeningStatus(false, "09:00");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void atStartOfFirstShift() {
        converter = new Days2PlaceOpeningStatusConverter();
        converter.setCurrentDate(getDate(DayName.MONDAY, 9, 0));

        result = converter.convert(workingDays);
        expected = new OpeningStatus(true, "17:00");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void atMiddleOfFirstShift() {
        converter = new Days2PlaceOpeningStatusConverter();
        converter.setCurrentDate(getDate(DayName.MONDAY, 12, 0));

        result = converter.convert(workingDays);
        expected = new OpeningStatus(true, "17:00");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void atEndOfFirstShift() {
        converter = new Days2PlaceOpeningStatusConverter();
        converter.setCurrentDate(getDate(DayName.MONDAY, 17, 0));

        result = converter.convert(workingDays);
        expected = new OpeningStatus(false, "18:00");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void betweenShifts() {
        Days2PlaceOpeningStatusConverter converter = new Days2PlaceOpeningStatusConverter();
        converter.setCurrentDate(getDate(DayName.MONDAY, 17, 30));

        result = converter.convert(workingDays);
        expected = new OpeningStatus(false, "18:00");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void atStartOfSecondShift() {
        converter = new Days2PlaceOpeningStatusConverter();
        converter.setCurrentDate(getDate(DayName.MONDAY, 18, 0));

        result = converter.convert(workingDays);
        expected = new OpeningStatus(true, "20:00");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void atMiddleOfSecondShift() {
        converter = new Days2PlaceOpeningStatusConverter();
        converter.setCurrentDate(getDate(DayName.MONDAY, 19, 0));

        result = converter.convert(workingDays);
        expected = new OpeningStatus(true, "20:00");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void atEndOfSecondShift() {
        converter = new Days2PlaceOpeningStatusConverter();
        converter.setCurrentDate(getDate(DayName.MONDAY, 20, 0));

        result = converter.convert(workingDays);
        expected = new OpeningStatus(false, "09:00 Tuesday");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void afterShifts() {
        Days2PlaceOpeningStatusConverter converter = new Days2PlaceOpeningStatusConverter();
        converter.setCurrentDate(getDate(DayName.MONDAY, 22, 0));

        result = converter.convert(workingDays);
        expected = new OpeningStatus(false, "09:00 Tuesday");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void notWorkingDayAtAll() {
        Days2PlaceOpeningStatusConverter converter = new Days2PlaceOpeningStatusConverter();
        converter.setCurrentDate(getDate(DayName.SATURDAY, 12, 0));

        result = converter.convert(workingDays);
        expected = new OpeningStatus(false, "09:00 Monday");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    private List<Day> getWorkingDays() {
        Shift firstShift = mock(Shift.class);
        when(firstShift.getStart()).thenReturn("09:00");
        when(firstShift.getEnd()).thenReturn("17:00");

        Shift secondShift = mock(Shift.class);
        when(secondShift.getStart()).thenReturn("18:00");
        when(secondShift.getEnd()).thenReturn("20:00");

        Day mockDayToday = mock(Day.class);
        when(mockDayToday.getName()).thenReturn(DayName.MONDAY);
        when(mockDayToday.getShifts()).thenReturn(Arrays.asList(firstShift, secondShift));

        Day mockDayTomorrow = mock(Day.class);
        when(mockDayTomorrow.getName()).thenReturn(DayName.TUESDAY);
        when(mockDayTomorrow.getShifts()).thenReturn(Arrays.asList(firstShift, secondShift));

        return Arrays.asList(mockDayToday, mockDayTomorrow);
    }

    // 1.1.2023. -> SUNDAY
    // 2.1.2023. -> MONDAY
    // 3.1.2023. -> TUESDAY
    private Date getDate(DayName dayName, int hour, int minute) {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, getIntFromDayName(dayName), hour, minute);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private int getIntFromDayName(DayName dayName) {
        if (Objects.requireNonNull(dayName) == DayName.MONDAY) {
            return 2;
        } else if (dayName == DayName.TUESDAY) {
            return 3;
        }
        return 7;
    }
}


