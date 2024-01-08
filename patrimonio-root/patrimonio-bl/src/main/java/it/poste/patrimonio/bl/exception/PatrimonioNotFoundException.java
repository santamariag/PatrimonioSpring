package it.poste.patrimonio.bl.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PatrimonioNotFoundException extends RuntimeException {

	/**
	 *
	 */
	private String message;
	
	private String ndg;


	private static final long serialVersionUID = 6178074345643395878L;

	public PatrimonioNotFoundException(String message, String ndg) {
		super(message);
		this.message = message;
		this.ndg = ndg;
	}

}
