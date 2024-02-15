package it.poste.patrimonio.bl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.poste.patrimonio.bl.service.IFoeService;
import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.db.model.common.Position;
import it.poste.patrimonio.db.repository.IFoeRepository;
import it.poste.patrimonio.itf.mapper.FoeMapper;
import it.poste.patrimonio.itf.model.FoeDTO;
import it.poste.patrimonio.rs.specs.model.DettaglioPatrimonioTypeTypeNs2;
import it.poste.patrimonio.rs.specs.model.PatrimonioClienteOutputElementNs1;

@Service
public class FoeService implements IFoeService {
	
	@Autowired
	private IFoeRepository foeRepository;
	
	@Autowired
	private FoeMapper mapper;


	@Override
	public PatrimonioClienteOutputElementNs1 findByNdgs(List<String> ndgs) {
		
		List<Foe> foes= foeRepository.findByNdgIn(ndgs);
		
		if(foes.isEmpty())
			return null;
		
		List<Position> allPositions=new ArrayList<>();
		
		for (Foe foe : foes) {
			allPositions.addAll(foe.getPatrimonioOld().getPosizioni());
		}
		
		PatrimonioClienteOutputElementNs1 output= new PatrimonioClienteOutputElementNs1();
		List<DettaglioPatrimonioTypeTypeNs2> dettaglioPatrimonio =mapper.modelListToApiList(allPositions);
		
		output.setDettaglioPatrimonio(dettaglioPatrimonio);
		
		return output;
		
		
	}

	@Override
	public void add(Foe foe) {
		
		foeRepository.save(foe);
		
	}
	
	@Override
	public void add(FoeDTO foe) {
		
		foeRepository.save(mapper.apiToModel(foe));	
	}

}
