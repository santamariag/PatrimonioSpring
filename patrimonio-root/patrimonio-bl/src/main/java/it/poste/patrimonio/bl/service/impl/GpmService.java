package it.poste.patrimonio.bl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.poste.patrimonio.bl.service.IGpmService;
import it.poste.patrimonio.db.model.Gpm;
import it.poste.patrimonio.db.model.common.Position;
import it.poste.patrimonio.db.repository.IGpmRepository;
import it.poste.patrimonio.itf.mapper.GpmMapper;
import it.poste.patrimonio.itf.model.GpmDTO;
import it.poste.patrimonio.rs.specs.model.DettaglioPatrimonioTypeTypeNs2;
import it.poste.patrimonio.rs.specs.model.PatrimonioClienteOutputElementNs1;

@Service
public class GpmService implements IGpmService {
	
	@Autowired
	private IGpmRepository gpmRepository;
	
	@Autowired
	private GpmMapper mapper;


	@Override
	public PatrimonioClienteOutputElementNs1 findByNdgs(List<String> ndgs) {
		
		List<Gpm> gpms= gpmRepository.findByNdgIn(ndgs);
		
		if(gpms.isEmpty())
			return null;
		
		List<Position> allPositions=new ArrayList<>();
		
		for (Gpm gpm : gpms) {
			if(gpm.getPatrimonioOld()!=null)
				allPositions.addAll(gpm.getPatrimonioOld().getPosizioni());
		}
		
		PatrimonioClienteOutputElementNs1 output= new PatrimonioClienteOutputElementNs1();
		List<DettaglioPatrimonioTypeTypeNs2> dettaglioPatrimonio =mapper.modelListToApiList(allPositions);
		
		output.setDettaglioPatrimonio(dettaglioPatrimonio);
		
		return output;
		
		
	}

	@Override
	public void add(Gpm gpm) {
		
		gpmRepository.save(gpm);
		
	}
	
	@Override
	public void add(GpmDTO gpm) {
		
		gpmRepository.save(mapper.apiToModel(gpm));	
	}

}
