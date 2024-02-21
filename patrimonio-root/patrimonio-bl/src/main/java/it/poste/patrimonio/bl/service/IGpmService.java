package it.poste.patrimonio.bl.service;

import java.util.List;


import it.poste.patrimonio.db.model.Gpm;
import it.poste.patrimonio.itf.model.GpmDTO;
import it.poste.patrimonio.rs.specs.model.PatrimonioClienteOutputElementNs1;


public interface IGpmService {
	
	public PatrimonioClienteOutputElementNs1 findByNdgs(List<String> ndgs);
	
	public void add(Gpm gpm);
	
	public void add(GpmDTO gpm);

	public void update(String id, Gpm gpm);
	
	

}
