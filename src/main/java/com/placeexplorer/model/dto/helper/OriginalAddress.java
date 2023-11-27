package com.placeexplorer.model.dto.helper;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OriginalAddress {

    private OriginalWhere where;
    private List<OriginalContact> contacts;

}
