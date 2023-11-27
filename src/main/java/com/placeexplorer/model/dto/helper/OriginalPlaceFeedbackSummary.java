package com.placeexplorer.model.dto.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OriginalPlaceFeedbackSummary {

    @JsonProperty("ratings_count")
    private int ratingsCount;

    @JsonProperty("average_rating")
    private double averageRating;

}
