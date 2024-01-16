package it.poste.patrimonio.db.model;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "gpms")
@Data
@CompoundIndexes({
    @CompoundIndex(name = "idx-ndg_prod_idprod", def = "{'ndg' : 1, 'patrimonioOld.posizioni.cstrfin': 1, 'patrimonioOld.posizioni.idProd': 1}"),
    @CompoundIndex(name = "idx-fiscalcode_prod_idprod", def = "{'fiscalCode' : 1, 'patrimonioOld.posizioni.cstrfin': 1, 'patrimonioOld.posizioni.idProd': 1}")
})
public class Gpm {
	
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
