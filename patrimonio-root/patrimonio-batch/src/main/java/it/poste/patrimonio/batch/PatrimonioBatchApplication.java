package it.poste.patrimonio.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableBatchProcessing
@EnableAsync
@ComponentScan(basePackages = "it.poste.patrimonio")
@EnableMongoRepositories(basePackages = "it.poste.patrimonio")
@EnableRetry
public class PatrimonioBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatrimonioBatchApplication.class, args);
	}
	
}
