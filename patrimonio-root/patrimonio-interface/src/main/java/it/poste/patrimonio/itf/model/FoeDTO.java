package it.poste.patrimonio.itf.model;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import it.poste.patrimonio.db.model.Deposit;
import it.poste.patrimonio.db.model.ExternalKeys;
import lombok.Data;

@Data
public class FoeDTO {
	
	@Id
	private String ndg;
	@Version
	private Long version;
	
	private String fiscalCode;
	
	private String rapporto;

	private Deposit deposit;
	
	private String internalCustomerCode;
	
	private String customerDescr;
	
	private ExternalKeys externalKeys; // TODO forse non servono
	
	private PatrimonioOldDTO patrimonioOld;
	private PatrimonioDTO patrimonio;
	
	private List<EventDTO> events;

}
