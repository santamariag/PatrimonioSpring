package it.poste.patrimonio.db.model;

import lombok.Data;

/**
 * Mapping for GPM/FOE
 * @author santamariag
 * TODO Titoli
 */
@Data
public class Position {
	
	private Detail detail;
	
	private InternalCounters internalCounters;
	
}
