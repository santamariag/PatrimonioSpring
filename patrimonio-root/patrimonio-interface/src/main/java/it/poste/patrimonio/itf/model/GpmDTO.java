package it.poste.patrimonio.itf.model;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import lombok.Data;

@Data
public class GpmDTO {
	
	@Id
	private String ndg;
	@Version
	private Long version;
	
	private ExternalKeysDTO externalKeys;
	private PatrimonioOldDTO patrimonioOld;
	private PatrimonioDTO patrimonio;
	
	private List<EventDTO> events;
	
	
	

}
