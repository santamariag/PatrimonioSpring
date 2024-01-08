package it.poste.patrimonio.batch.bl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix="job.multi-exec")
@Getter
@Setter
public class JobNameConfig {
	
	private boolean always;
	private boolean allowed;
	private String nameSuffix;

}
