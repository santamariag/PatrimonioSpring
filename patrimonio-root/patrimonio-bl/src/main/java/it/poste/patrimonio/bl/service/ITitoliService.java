package it.poste.patrimonio.bl.service;

import java.util.List;

import it.poste.patrimonio.db.model.titoli.Titoli;
import it.poste.patrimonio.itf.model.TitoliDTO;
import it.poste.patrimonio.rs.specs.model.PatrimonioClienteOutputElementNs1;


public interface ITitoliService {
	
	public PatrimonioClienteOutputElementNs1 findByNdgs(List<String> ndgs);
	
	public void add(Titoli titoli);
	
	public void add(TitoliDTO gpm);
	
	

}
