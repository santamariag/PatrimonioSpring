package it.poste.patrimonio.bl.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import it.poste.patrimonio.bl.service.IFondiService;
import it.poste.patrimonio.rs.specs.model.PatrimonioClienteOutputElementNs1;

@Service
public class FondiService implements IFondiService {

	@Override
	public PatrimonioClienteOutputElementNs1 findByNdgs(List<String> ndgs) {
		// TODO Auto-generated method stub
		return null;
	}

}
