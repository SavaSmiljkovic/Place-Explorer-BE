package com.placeexplorer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.errorHandler(new CustomResponseErrorHandler()).build();
    }

    private static class CustomResponseErrorHandler implements ResponseErrorHandler {

        private final Logger logger = LoggerFactory.getLogger(CustomResponseErrorHandler.class);

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return !response.getStatusCode().is2xxSuccessful();
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            if (response.getStatusCode().is5xxServerError()) {
                logger.error("Server Error Occurred While Using RestTemplate");
            } else if (response.getStatusCode().is4xxClientError()) {
                logger.error("Client Error Occurred While Using RestTemplate");
            } else {
                logger.error("Redirection Error Occurred While Using RestTemplate");
            }
        }
    }

}
