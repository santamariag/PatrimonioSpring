package it.poste.patrimonio.db.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "secs")
@Data
public class Titoli {
	
	@Id
	private String ndg;
	@Version
	private Long version;
	
	private String fiscalCode;
	
	private String rapporto; //concatenazione di filiale agenzia numero e rubrica (vedi Deposit)

	private Deposit deposit;
	
	private String internalCustomerCode;
	
	private String customerDescr;
	
	private ExternalKeys externalKeys; // TODO forse non servono
	
	private PatrimonioOld patrimonioOld;
	
	private Patrimonio patrimonio;
	
}
