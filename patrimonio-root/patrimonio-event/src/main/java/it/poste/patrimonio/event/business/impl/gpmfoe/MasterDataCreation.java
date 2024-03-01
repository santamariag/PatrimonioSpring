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
public class MasterDataCreation implements IGpmFoeBusinessEvent {

	private String ndg;
	private String branch; 	// filiale
	private String agency; 	// agenzia
	private String doss;	// numero
	private String index; 	//rubrica
	private String clientDesc; 
	private String clientIntCode; // codice interno cliente
	private String status;
	private String taxIdCode; // codice fiscale
	private String institute; // (tipo rapporto 1 GPM, 5 FOE)


	@Override
	public String getKey() { return institute + "#" + ndg; }


}
