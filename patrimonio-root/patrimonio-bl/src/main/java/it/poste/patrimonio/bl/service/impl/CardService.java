package it.poste.patrimonio.bl.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import it.poste.patrimonio.bl.service.ICardService;
import it.poste.patrimonio.rs.specs.model.PatrimonioClienteOutputElementNs1;

@Service
public class CardService implements ICardService {

	@Override
	public PatrimonioClienteOutputElementNs1 findByNdgs(List<String> ndgs) {
		// TODO Auto-generated method stub
		return null;
	}

}
