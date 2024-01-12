package it.poste.patrimonio.bl.service;

import java.util.List;

import it.poste.patrimonio.rs.specs.model.PatrimonioClienteOutputElementNs1;

public interface IRNdgService {
	
	public PatrimonioClienteOutputElementNs1 findByNdgs(List<String> ndgs);


}
