package it.poste.patrimonio.bl.service;

import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.db.model.Gpm;
import it.poste.patrimonio.db.model.Titoli;
import it.poste.patrimonio.rs.specs.model.DettaglioPatrimonioInput;
import it.poste.patrimonio.rs.specs.model.PatrimonioClienteOutputElementNs1;

public interface IOrchestratorService {
	
	public PatrimonioClienteOutputElementNs1 retrievePatrimonioCliente(DettaglioPatrimonioInput dettaglioPatrimonioInput);

	public void add(Gpm gpm);

	public void add(Foe foe);

	public void add(Titoli titoli);

}
