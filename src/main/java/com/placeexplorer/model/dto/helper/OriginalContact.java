package com.placeexplorer.model.dto.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OriginalContact {

    @JsonProperty("call_link")
    private String callLink;

}
