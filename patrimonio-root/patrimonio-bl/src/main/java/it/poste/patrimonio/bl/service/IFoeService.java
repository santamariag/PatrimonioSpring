package it.poste.patrimonio.bl.service;

import java.util.List;

import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.itf.model.FoeDTO;
import it.poste.patrimonio.rs.specs.model.PatrimonioClienteOutputElementNs1;


public interface IFoeService {
	
	public PatrimonioClienteOutputElementNs1 findByNdgs(List<String> ndgs);
	
	public void add(Foe foe);
	
	public void add(FoeDTO foe);
	
	

}
