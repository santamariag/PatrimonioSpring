package it.poste.patrimonio.kconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = "it.poste.patrimonio")
@EnableMongoRepositories(basePackages = "it.poste.patrimonio")
@EnableTransactionManagement
public class PatrimonioKConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatrimonioKConsumerApplication.class, args);
	}
	
	

}
