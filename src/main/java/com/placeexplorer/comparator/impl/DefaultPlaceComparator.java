package com.placeexplorer.comparator.impl;

import com.placeexplorer.comparator.PlaceComparator;
import com.placeexplorer.model.Address;
import com.placeexplorer.model.Day;
import com.placeexplorer.model.Shift;
import com.placeexplorer.model.entity.Place;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DefaultPlaceComparator implements PlaceComparator {

    @Override
    public boolean shouldUpdate(Place newPlace, Place oldPlace) {
        return !Objects.equals(newPlace.getType(), oldPlace.getType()) ||
            !Objects.equals(newPlace.getName(), oldPlace.getName()) ||
            !Objects.equals(newPlace.getAddress(), oldPlace.getAddress()) ||
            newPlace.getRatingCount() != oldPlace.getRatingCount() ||
            newPlace.getRatingAverage() != oldPlace.getRatingAverage() ||
            addressesChanged(newPlace.getAddresses(), oldPlace.getAddresses()) ||
            daysChanged(newPlace.getDays(), oldPlace.getDays());
    }

    private boolean addressesChanged(List<Address> newAddresses, List<Address> oldAddresses) {
        if (newAddresses.size() != oldAddresses.size()) {
            return true;
        }

        for (int i = 0; i < newAddresses.size(); i++) {
            if (!Objects.equals(newAddresses.get(i).getState(), oldAddresses.get(i).getState()) ||
                !Objects.equals(newAddresses.get(i).getCity(), oldAddresses.get(i).getCity()) ||
                !Objects.equals(newAddresses.get(i).getStreet(), oldAddresses.get(i).getStreet()) ||
                !Objects.equals(newAddresses.get(i).getHouseNumber(), oldAddresses.get(i).getHouseNumber()) ||
                newAddresses.get(i).getLatitude() != oldAddresses.get(i).getLatitude() ||
                newAddresses.get(i).getLongitude() != oldAddresses.get(i).getLongitude() ||
                phoneNumbersChanged(newAddresses.get(i).getPhoneNumbers(), oldAddresses.get(i).getPhoneNumbers())) {
                return true;
            }
        }

        return false;
    }

    private boolean phoneNumbersChanged(List<String> newPhoneNumbers, List<String> oldPhoneNumbers) {
        if (newPhoneNumbers.size() != oldPhoneNumbers.size()) {
            return true;
        }

        for (int i = 0; i < newPhoneNumbers.size(); i++) {
            if (!Objects.equals(newPhoneNumbers.get(i), oldPhoneNumbers.get(i))) {
                return true;
            }
        }

        return false;
    }

    private boolean daysChanged(List<Day> newDays, List<Day> oldDays) {
        if (newDays.size() != oldDays.size()) {
            return true;
        }

        for (int i = 0; i < newDays.size(); i++) {
            if (!Objects.equals(newDays.get(i).getName().toString(), oldDays.get(i).getName().toString()) ||
                shiftsChanged(newDays.get(i).getShifts(), oldDays.get(i).getShifts())) {
                return true;
            }
        }

        return false;
    }

    private boolean shiftsChanged(List<Shift> newShifts, List<Shift> oldShifts) {
        if (newShifts.size() != oldShifts.size()) {
            return true;
        }

        for (int i = 0; i < newShifts.size(); i++) {
            if (!Objects.equals(newShifts.get(i).getStart(), oldShifts.get(i).getStart()) ||
                !Objects.equals(newShifts.get(i).getEnd(), oldShifts.get(i).getEnd())) {
                return true;
            }
        }

        return false;
    }

}
