package it.poste.patrimonio.db.model;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "gpms")
@Data
public class Gpm {
	
	@Id
	private String ndg;
	@Version
	private Long version;
	
	private ExternalKeys externalKeys;
	private PatrimonioOld patrimonioOld;
	private Patrimonio patrimonio;
	
	private List<Event> events;
	
	
	

}
