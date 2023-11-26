package com.placeexplorer.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.placeexplorer.model.Shift;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PlaceOriginalDTO {

    @JsonProperty("entry_type")
    private String entryType;

    @JsonProperty("local_entry_id")
    private String localEntryId;

    @JsonProperty("place_feedback_summary")
    private PlaceFeedbackSummary placeFeedbackSummary;

    @JsonProperty("displayed_what")
    private String displayedWhat;

    @JsonProperty("displayed_where")
    private String displayedWhere;

    @JsonProperty("opening_hours")
    private OpeningHours openingHours;

    private List<Address> addresses;

    @Getter
    @Setter
    public static class PlaceFeedbackSummary {

        @JsonProperty("ratings_count")
        private int ratingsCount;

        @JsonProperty("average_rating")
        private double averageRating;
    }

    @Getter
    @Setter
    public static class Address {

        private Where where;
        private List<Contact> contacts;
    }

    @Getter
    @Setter
    public static class Contact {

        @JsonProperty("call_link")
        private String callLink;
    }

    @Getter
    @Setter
    public static class Where {

        @JsonProperty("house_number")
        private String houseNumber;
        private String street;
        private String city;
        private String state;
        private Geography geography;
    }

    @Getter
    @Setter
    public static class Geography {

        private Location location;
    }

    @Getter
    @Setter
    public static class Location {

        private double lat;
        private double lon;
    }

    @Getter
    @Setter
    public static class OpeningHours {

        private Map<String, List<Shift>> days;
    }

}
