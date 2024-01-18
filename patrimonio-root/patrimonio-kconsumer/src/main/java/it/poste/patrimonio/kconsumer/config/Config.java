package it.poste.patrimonio.kconsumer.config;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;


@Configuration
@EnableConfigurationProperties({
	KafkaConfig.class
})
public class Config {

	private static final String DATE_FORMAT = "yyyy-MM-dd";


	@Bean
	@Primary
	ObjectMapper objectMapper() {

		JavaTimeModule module = new JavaTimeModule();
		module.addSerializer(new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
		ObjectMapper mapper=new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		mapper.registerModule(module);

		return mapper;
	}

}
