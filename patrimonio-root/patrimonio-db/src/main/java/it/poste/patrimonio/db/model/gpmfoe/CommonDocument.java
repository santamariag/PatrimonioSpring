package it.poste.patrimonio.db.model.gpmfoe;


import it.poste.patrimonio.db.constants.Status;
import it.poste.patrimonio.db.model.common.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Data;

@Data
@CompoundIndexes({
    @CompoundIndex(name = "idx-ndg_prod_idprod", def = "{'ndg' : 1, 'patrimonioOld.posizioni.cstrfin': 1, 'patrimonioOld.posizioni.idProd': 1}"),
    @CompoundIndex(name = "idx-rapporto_prod_idprod", def = "{'rapporto' : 1, 'patrimonioOld.posizioni.cstrfin': 1, 'patrimonioOld.posizioni.idProd': 1}")
})
public class CommonDocument {
	
	@Id
	private ObjectId id;
	
	@Indexed
	private String ndg;
	
	@Version
	private Long version;
	
	/**
	 * flag per indicare se lo stato del documento è completo
	 * da aggiungere in un indice assieme all'ndg per la ricerca da parte dell'API
	 */
	private boolean complete; 
	
	/**
	 * da popolare coerentemente con gli eventi di blocco/Sblocco (da capire se esiste) anagrafica
	 * il documento nasce con valore ATTIVO
	 * da aggiungere eventualmente all'indice composto insieme a ndg e complete
	 */
	private Status status; //BLOCCATO-ATTIVO
	
	private String fiscalCode;
	
	private String rapporto; //concatenazione di filiale agenzia numero e rubrica (vedi Deposit)

	private Deposit deposit;
	
	private String internalCustomerCode;
	
	private String customerDescr;
	
	private ExternalKeys externalKeys; // TODO forse non servono
	
	private PatrimonioOld patrimonioOld;
	
	private Patrimonio patrimonio;
	
}
