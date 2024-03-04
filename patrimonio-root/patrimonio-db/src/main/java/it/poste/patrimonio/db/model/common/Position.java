package it.poste.patrimonio.db.model.common;

import it.poste.patrimonio.db.model.gpmfoe.InternalCountersGpmFoe;
import it.poste.patrimonio.db.model.titoli.InternalCountersTitoli;
import lombok.Data;

/**
 * Mapping for GPM/FOE
 * @author santamariag
 * TODO Titoli
 */
@Data
public class Position {
	
	private Detail detail;
	
	private InternalCountersGpmFoe internalCountersGpmFoe;

	private InternalCountersTitoli internalCountersTitoli;
	public Position(Detail detail) {
		this.setDetail(detail);
		this.setInternalCountersGpmFoe(new InternalCountersGpmFoe());
		this.setInternalCountersTitoli(new InternalCountersTitoli());
	}
}