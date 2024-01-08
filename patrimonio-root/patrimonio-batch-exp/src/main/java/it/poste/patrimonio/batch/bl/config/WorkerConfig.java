package it.poste.patrimonio.batch.bl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix="worker")
@Getter
@Setter
public class WorkerConfig {

	
	private int pageSize;
	private Long maxNumber;
	private int  chunkSize;
}
