package it.poste.patrimonio.rs.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.poste.patrimonio.rs.api.exception.ErrorResponseProvider;
import it.poste.patrimonio.rs.specs.model.EsitoTypeTypeNs2;

@Configuration
public class GlobalExceptionHandlerConfig {
	
	@Bean
	@ConditionalOnMissingBean
	ErrorResponseProvider errorResponseProvider() {
		return (faultCode, errors) -> {
			EsitoTypeTypeNs2 error = new EsitoTypeTypeNs2();
			error.esito(faultCode);
			error.setDettaglioErrore(errors);

			return error;
		};
	}

}
