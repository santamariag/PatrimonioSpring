package it.poste.patrimonio.bl.service;

import it.poste.patrimonio.db.model.gpmfoe.Foe;
import it.poste.patrimonio.db.model.gpmfoe.Gpm;
import it.poste.patrimonio.db.model.titoli.Titoli;
import it.poste.patrimonio.rs.specs.model.DettaglioPatrimonioInput;
import it.poste.patrimonio.rs.specs.model.PatrimonioClienteOutputElementNs1;

public interface IOrchestratorService {
	
	public PatrimonioClienteOutputElementNs1 retrievePatrimonioCliente(DettaglioPatrimonioInput dettaglioPatrimonioInput);

	public void add(Gpm gpm);

	public void add(Foe foe);

	public void add(Titoli titoli);

}
