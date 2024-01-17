package it.poste.patrimonio.bl.util;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import it.poste.patrimonio.db.model.Position;

@Component
public class PositionUtil {
	
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

}
