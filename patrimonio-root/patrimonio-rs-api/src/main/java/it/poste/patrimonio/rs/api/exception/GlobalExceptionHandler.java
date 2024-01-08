package it.poste.patrimonio.rs.api.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.poste.patrimonio.bl.exception.PatrimonioNotFoundException;
import it.poste.patrimonio.rs.specs.model.DettaglioErroreTypeTypeNs2;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	@Setter
	private ErrorResponseProvider errorResponseProvider;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> globalExceptionHandler(Exception ex, WebRequest request) {
		log.error("Internal error", ex);

		Object response = errorResponseProvider.provideResponse(ErrorCodes.GENERIC_ERROR, null);

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	@ExceptionHandler(PatrimonioNotFoundException.class)
	public ResponseEntity<Object> notfoundExceptionHandler(Exception ex, WebRequest request) {
		log.error("Not found error", ex);
		
		DettaglioErroreTypeTypeNs2 detail = new DettaglioErroreTypeTypeNs2();
		PatrimonioNotFoundException specEx=PatrimonioNotFoundException.class.cast(ex);
		List<DettaglioErroreTypeTypeNs2> detailsList = new ArrayList<>();
		detail.setNdg(specEx.getNdg());
		detail.setCodiceErrore(ErrorCodes.RESOURCE_NOT_FOUND);
		detail.setDescrizioneErrore(ErrorCodes.RESOURCE_NOT_FOUND_DESC);
		detailsList.add(detail);
		Object response = errorResponseProvider.provideResponse(ErrorCodes.RESOURCE_NOT_FOUND, detailsList);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	

	@ExceptionHandler({DataIntegrityViolationException.class})
	protected ResponseEntity<Object> handlePersistenceException(final DataIntegrityViolationException ex) {

		Object response = errorResponseProvider.provideResponse(ErrorCodes.FUNCTIONAL_ERROR,
				null);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
