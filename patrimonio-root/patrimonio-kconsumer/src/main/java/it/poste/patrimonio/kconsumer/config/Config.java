package it.poste.patrimonio.kconsumer.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
@EnableConfigurationProperties({
	KafkaConfig.class
})
public class Config {

    @Bean
    ObjectMapper objectMapper() {

        return new ObjectMapper();
    }

}
