package com.placeexplorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableMongoRepositories
@EnableAsync
@ImportResource({"classpath*:placeexplorer-beans.xml"})
public class PlaceExplorerBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaceExplorerBeApplication.class, args);
	}

}
