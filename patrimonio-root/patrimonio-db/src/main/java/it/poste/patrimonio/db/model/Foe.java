package it.poste.patrimonio.db.model;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "foes")
@Data
public class Foe {
	
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
	
	private List<Event> events;

}
