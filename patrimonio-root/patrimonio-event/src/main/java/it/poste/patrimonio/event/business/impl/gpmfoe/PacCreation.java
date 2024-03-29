package it.poste.patrimonio.event.business.impl.gpmfoe;

import it.poste.patrimonio.event.business.model.IGpmFoeBusinessEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PacCreation  implements IGpmFoeBusinessEvent {
	
	private String ndg;
	private String clientIntCode; //codice interno cliente
	private String institute; // tipo rapporto 1 GPM
	private String productCode;
	private String productId;
	private String pacFlag;//presenza PAC se stato = 0,1 sommare +1 

	@Override
	public String getKey() {
		return institute + "#" + ndg;
	}

}
