package com.placeexplorer.model.dto.helper;

import com.placeexplorer.model.Shift;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class OriginalOpeningHours {

    private Map<String, List<Shift>> days;
}
