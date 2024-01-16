package it.poste.patrimonio.batch.bl.processor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import it.poste.patrimonio.db.model.Gpm;
import it.poste.patrimonio.db.model.Position;
import it.poste.patrimonio.db.repository.IGpmRepository;
import it.poste.patrimonio.itf.model.MFMBalanceDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MFMBalanceToGpmProcessor implements ItemProcessor<MFMBalanceDTO, List<Gpm>>{


	@Value("#{stepExecution}")
	private StepExecution stepExecution;
	
	private final IGpmRepository gpmRepository;
	
	
	public MFMBalanceToGpmProcessor(IGpmRepository gpmRepository) {
		
		this.gpmRepository = gpmRepository;
	}
	
	public void setStepExecution(final StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}



	@Override
	public List<Gpm> process(MFMBalanceDTO item) throws Exception {
		
		log.info("MDM Item {}", item);
		
		String productMifid=retrieveProductMifid(item.getProduct());
		
		String referenceDate=(String)this.stepExecution.getExecutionContext().get("referenceDate");
		
		List<Gpm> gpmList=gpmRepository.findByKey(item.getFiscalCode(), productMifid, item.getProductId());
		
		if(gpmList.isEmpty())
			return null;
		
		
		gpmList.forEach(g->{
			log.info("G "+g);;
			g.getPatrimonioOld().getPosizioni().forEach(p->{
				if(productMifid.equals(p.getCstrfin())
						&& item.getProductId().equals(p.getIdProd())){
					p.setCs(item.getCtv());
					p.setQs(item.getQtaSub().subtract(item.getQtaRef()));
					p.setQqta(calculateQqta(p));
					p.setIvalbas(calculateCtv(p));
					p.setDulprz(LocalDate.parse(referenceDate));
				}
			});
		});
		
		return gpmList;	
		
	}


	private BigDecimal calculateCtv(Position p) {
		//CS+CSS-CRS
		BigDecimal cs=p.getCs()!=null?p.getCs():BigDecimal.ZERO;
		BigDecimal css=p.getCss()!=null?p.getCss():BigDecimal.ZERO;
		BigDecimal crs=p.getCrs()!=null?p.getCrs():BigDecimal.ZERO;
		
		return cs.add(css).subtract(crs);
	}


	private BigDecimal calculateQqta(Position p) {
		// QS+QSS-QRS
		BigDecimal qs=p.getQs()!=null?p.getQs():BigDecimal.ZERO;
		BigDecimal qss=p.getQss()!=null?p.getQss():BigDecimal.ZERO;
		BigDecimal qrs=p.getQrs()!=null?p.getQrs():BigDecimal.ZERO;
		
		return qs.add(qss).subtract(qrs);
	}


	private String retrieveProductMifid(String product) {
		// Invoke external service. For now retrieve the same value
		return product;
	}

}
