package it.poste.patrimonio.batch.bl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix="file-config")
@Getter
@Setter
public class FileConfig {
		
	private String inputPath;
	private String fileName;
	private String processingPath;
	private String completedPath;
	private String discardedPath;
	private String noOpPath;
	
}
