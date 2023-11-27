package com.placeexplorer.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.placeexplorer.model.dto.helper.OriginalAddress;
import com.placeexplorer.model.dto.helper.OriginalOpeningHours;
import com.placeexplorer.model.dto.helper.OriginalPlaceFeedbackSummary;
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
    private OriginalPlaceFeedbackSummary placeFeedbackSummary;

    @JsonProperty("displayed_what")
    private String displayedWhat;

    @JsonProperty("displayed_where")
    private String displayedWhere;

    @JsonProperty("opening_hours")
    private OriginalOpeningHours openingHours;

    private List<OriginalAddress> addresses;

}
