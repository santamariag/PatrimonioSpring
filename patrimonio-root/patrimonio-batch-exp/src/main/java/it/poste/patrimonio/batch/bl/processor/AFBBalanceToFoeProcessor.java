package it.poste.patrimonio.batch.bl.processor;

import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.db.model.Position;
import it.poste.patrimonio.db.repository.IFoeRepository;
import it.poste.patrimonio.itf.model.AFBBalanceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class AFBBalanceToFoeProcessor implements ItemProcessor<AFBBalanceDTO, List<Foe>>{


	@Value("#{stepExecution}")
	private StepExecution stepExecution;
	
	private final IFoeRepository foeRepository;
	
	
	public AFBBalanceToFoeProcessor(IFoeRepository foeRepository) {
		
		this.foeRepository = foeRepository;
	}
	
	public void setStepExecution(final StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}



	@Override
	public List<Foe> process(AFBBalanceDTO item) throws Exception {
		
		log.info("AFB Item {}", item);
		String productPrevinet=item.getProduct();
		String rapporto=item.getBranch().concat(item.getAgency()).concat(item.getNumber()).concat(item.getIndex());
		List<Foe> foeList=foeRepository.findByKey(rapporto, productPrevinet, item.getProductId());
		
		if(foeList.isEmpty())
			return null;
		
		
		foeList.forEach(g->{
			log.info("F "+g);;
			g.getPatrimonioOld().getPosizioni().forEach(p->{
				if(productPrevinet.equals(p.getCstrfin())
						&& item.getProductId().equals(p.getIdProd())){
					p.setCs(item.getCtv());
					p.setQs(item.getQta());
					p.setQqta(calculateQqta(p));
					p.setIvalbas(calculateCtv(p));
					p.setDulprz(item.getReferenceDate());
					p.setIprzat(item.getPrice());
				}
			});
		});
		
		return foeList;	
		
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


}
