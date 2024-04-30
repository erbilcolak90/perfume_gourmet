package com.gourmet.perfume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages ="com.gourmet.perfume.repository.mongodb")
@EnableElasticsearchRepositories(basePackages = "com.gourmet.perfume.repository.elasticsearch")
public class PerfumeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerfumeApplication.class, args);
	}

}
