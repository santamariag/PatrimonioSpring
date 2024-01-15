package it.poste.patrimonio.bl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.poste.patrimonio.bl.service.ITitoliService;
import it.poste.patrimonio.db.model.Position;
import it.poste.patrimonio.db.model.Titoli;
import it.poste.patrimonio.db.repository.ITitoliRepository;
import it.poste.patrimonio.itf.mapper.TitoliMapper;
import it.poste.patrimonio.itf.model.TitoliDTO;
import it.poste.patrimonio.rs.specs.model.DettaglioPatrimonioTypeTypeNs2;
import it.poste.patrimonio.rs.specs.model.PatrimonioClienteOutputElementNs1;

@Service
public class TitoliService implements ITitoliService {
	
	@Autowired
	private ITitoliRepository titoliRepository;
	
	@Autowired
	private TitoliMapper mapper;


	@Override
	public PatrimonioClienteOutputElementNs1 findByNdgs(List<String> ndgs) {
		
		List<Titoli> titoli= titoliRepository.findByNdgIn(ndgs);
		
		
		List<Position> allPositions=new ArrayList<>();
		
		for (Titoli titolo : titoli) {
			allPositions.addAll(titolo.getPatrimonioOld().getPosizioni());
		}
		
		PatrimonioClienteOutputElementNs1 output= new PatrimonioClienteOutputElementNs1();
		List<DettaglioPatrimonioTypeTypeNs2> dettaglioPatrimonio =mapper.modelListToApiList(allPositions);
		
		output.setDettaglioPatrimonio(dettaglioPatrimonio);
		
		return output;
		
		
	}

	@Override
	public void add(Titoli titoli) {
		
		titoliRepository.save(titoli);
		
	}
	
	@Override
	public void add(TitoliDTO foe) {
		
		titoliRepository.save(mapper.apiToModel(foe));	
	}

}
