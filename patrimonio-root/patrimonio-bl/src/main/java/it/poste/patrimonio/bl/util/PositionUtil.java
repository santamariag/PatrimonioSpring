package it.poste.patrimonio.bl.util;

import java.math.BigDecimal;
import java.util.Collections;

import it.poste.patrimonio.bl.exception.PatrimonioNotFoundException;
import it.poste.patrimonio.db.model.CommonData;
import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.db.model.Gpm;
import it.poste.patrimonio.db.repository.IFoeRepository;
import it.poste.patrimonio.db.repository.IGpmRepository;
import org.springframework.stereotype.Component;

import it.poste.patrimonio.db.model.Position;

@Component
public class PositionUtil {

	IFoeRepository foeRepository;
	IGpmRepository gpmRepository;
	
	public BigDecimal calculateCtv(Position p) {
		//CS+CSS-CRS
		BigDecimal cs=p.getInternalCounters().getCs()!=null?p.getInternalCounters().getCs():BigDecimal.ZERO;
		BigDecimal css=p.getInternalCounters().getCss()!=null?p.getInternalCounters().getCss():BigDecimal.ZERO;
		BigDecimal crs=p.getInternalCounters().getCrs()!=null?p.getInternalCounters().getCrs():BigDecimal.ZERO;
		
		return cs.add(css).subtract(crs);
	}


	public BigDecimal calculateQqta(Position p) {
		// QS+QSS-QRS
		BigDecimal qs=p.getInternalCounters().getQs()!=null?p.getInternalCounters().getQs():BigDecimal.ZERO;
		BigDecimal qss=p.getInternalCounters().getQss()!=null?p.getInternalCounters().getQss():BigDecimal.ZERO;
		BigDecimal qrs=p.getInternalCounters().getQrs()!=null?p.getInternalCounters().getQrs():BigDecimal.ZERO;
		
		return qs.add(qss).subtract(qrs);
	}

	public CommonData findDataByClient(String institute, String client){
		return null; //TODO
	}

	public CommonData findDataByNdg(String institute, String ndg) {
		return switch (institute) {
			//TODO controlli
			case Constants.GPM_INST -> gpmRepository.findByNdgIn(Collections.singletonList(ndg)).get(0);
			case Constants.FOE_INST -> foeRepository.findByNdgIn(Collections.singletonList(ndg)).get(0);
			default -> throw new PatrimonioNotFoundException("Not found", ndg);
		};

	}

	public void saveData(String institute, CommonData data) {

		switch (institute) {
			case Constants.GPM_INST: {
				gpmRepository.save((Gpm) data);
				break;
			}
			case Constants.FOE_INST: {
				foeRepository.save((Foe) data);
				break;
			}
		}
	}

}
