package it.poste.patrimonio.itf.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import it.poste.patrimonio.db.model.common.Deposit;
import it.poste.patrimonio.db.model.common.ExternalKeys;
import lombok.Data;

@Data
public class GpmDTO {
	
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
	
}
