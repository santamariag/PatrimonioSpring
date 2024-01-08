package it.poste.patrimonio.rs.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "it.poste.patrimonio")
@EnableMongoRepositories(basePackages = "it.poste.patrimonio")
public class PatrimonioRsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatrimonioRsApiApplication.class, args);
	}

}
