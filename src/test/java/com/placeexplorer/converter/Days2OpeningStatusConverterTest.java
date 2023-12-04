package com.placeexplorer.converter;

import com.placeexplorer.model.Day;
import com.placeexplorer.model.DayName;
import com.placeexplorer.model.Shift;
import com.placeexplorer.model.OpeningStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class Days2OpeningStatusConverterTest {

    private OpeningStatus result;
    private OpeningStatus expected;

    @Test
    void beforeFirstShift() {
        Days2PlaceOpeningStatusConverter converter = Mockito.spy(new Days2PlaceOpeningStatusConverter());
        when(converter.getCurrentTime()).thenReturn(LocalDateTime.of(2023, 1, 2, 8, 0));

        result = converter.convert(getWorkingDays());
        expected = new OpeningStatus(false, "09:00");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void atStartOfFirstShift() {
        Days2PlaceOpeningStatusConverter converter = Mockito.spy(new Days2PlaceOpeningStatusConverter());
        when(converter.getCurrentTime()).thenReturn(LocalDateTime.of(2023, 1, 2, 9, 0));

        result = converter.convert(getWorkingDays());
        expected = new OpeningStatus(true, "17:00");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void atMiddleOfFirstShift() {
        Days2PlaceOpeningStatusConverter converter = Mockito.spy(new Days2PlaceOpeningStatusConverter());
        when(converter.getCurrentTime()).thenReturn(LocalDateTime.of(2023, 1, 2, 12, 0));

        result = converter.convert(getWorkingDays());
        expected = new OpeningStatus(true, "17:00");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void atEndOfFirstShift() {
        Days2PlaceOpeningStatusConverter converter = Mockito.spy(new Days2PlaceOpeningStatusConverter());
        when(converter.getCurrentTime()).thenReturn(LocalDateTime.of(2023, 1, 2, 17, 0));

        result = converter.convert(getWorkingDays());
        expected = new OpeningStatus(false, "18:00");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void betweenShifts() {
        Days2PlaceOpeningStatusConverter converter = Mockito.spy(new Days2PlaceOpeningStatusConverter());
        when(converter.getCurrentTime()).thenReturn(LocalDateTime.of(2023, 1, 2, 17, 30));

        result = converter.convert(getWorkingDays());
        expected = new OpeningStatus(false, "18:00");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void atStartOfSecondShift() {
        Days2PlaceOpeningStatusConverter converter = Mockito.spy(new Days2PlaceOpeningStatusConverter());
        when(converter.getCurrentTime()).thenReturn(LocalDateTime.of(2023, 1, 2, 18, 0));

        result = converter.convert(getWorkingDays());
        expected = new OpeningStatus(true, "20:00");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void atMiddleOfSecondShift() {
        Days2PlaceOpeningStatusConverter converter = Mockito.spy(new Days2PlaceOpeningStatusConverter());
        when(converter.getCurrentTime()).thenReturn(LocalDateTime.of(2023, 1, 2, 19, 0));

        result = converter.convert(getWorkingDays());
        expected = new OpeningStatus(true, "20:00");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void atEndOfSecondShift() {
        Days2PlaceOpeningStatusConverter converter = Mockito.spy(new Days2PlaceOpeningStatusConverter());
        when(converter.getCurrentTime()).thenReturn(LocalDateTime.of(2023, 1, 2, 20, 0));

        result = converter.convert(getWorkingDays());
        expected = new OpeningStatus(false, "09:00 Tuesday");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void afterShifts() {
        Days2PlaceOpeningStatusConverter converter = Mockito.spy(new Days2PlaceOpeningStatusConverter());
        when(converter.getCurrentTime()).thenReturn(LocalDateTime.of(2023, 1, 2, 22, 0));

        result = converter.convert(getWorkingDays());
        expected = new OpeningStatus(false, "09:00 Tuesday");

        assertEquals(expected.isOpen(), result.isOpen());
        assertEquals(expected.getTime(), result.getTime());
    }

    @Test
    void notWorkingDayAtAll() {
        Days2PlaceOpeningStatusConverter converter = Mockito.spy(new Days2PlaceOpeningStatusConverter());
        when(converter.getCurrentTime()).thenReturn(LocalDateTime.of(2023, 1, 7, 12, 0));

        result = converter.convert(getWorkingDays());
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
}


