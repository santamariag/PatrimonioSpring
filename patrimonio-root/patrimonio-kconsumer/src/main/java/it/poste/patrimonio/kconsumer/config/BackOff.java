package it.poste.patrimonio.kconsumer.config;

import lombok.Data;

@Data
public class BackOff {
	
	private Long interval;
	private Long maxAttempts;

}
