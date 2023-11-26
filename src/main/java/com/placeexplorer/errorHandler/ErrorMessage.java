package com.placeexplorer.errorHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("error")
public class ErrorMessage {

    private String general;
    private String originalPlaceNull;
    private String originalPlaceInvalid;
    private String uid;

}
