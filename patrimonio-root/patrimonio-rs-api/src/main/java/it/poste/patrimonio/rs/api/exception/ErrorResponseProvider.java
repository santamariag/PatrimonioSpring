package it.poste.patrimonio.rs.api.exception;

import java.util.List;

import it.poste.patrimonio.rs.specs.model.DettaglioErroreTypeTypeNs2;


@FunctionalInterface
public interface ErrorResponseProvider {

	Object provideResponse(String code, List<DettaglioErroreTypeTypeNs2> errorsDetails);
	
	

}
