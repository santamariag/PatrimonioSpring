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
		
		
		foeList.forEach(f->{
			log.info("F "+f);;
			f.getPatrimonioOld().getPosizioni().forEach(p->{
				if(productPrevinet.equals(p.getDetail().getCstrfin())
						&& item.getProductId().equals(p.getDetail().getIdProd())){
					p.getInternalCounters().setCs(item.getCtv());
					p.getInternalCounters().setQs(item.getQta());
					p.getDetail().setQqta(calculateQqta(p));
					p.getDetail().setIvalbas(calculateCtv(p));
					p.getDetail().setDulprz(item.getReferenceDate());
					p.getDetail().setIprzat(item.getPrice());
				}
			});
		});
		
		return foeList;	
		
	}


	private BigDecimal calculateCtv(Position p) {
		//CS+CSS-CRS
		BigDecimal cs=p.getInternalCounters().getCs()!=null?p.getInternalCounters().getCs():BigDecimal.ZERO;
		BigDecimal css=p.getInternalCounters().getCss()!=null?p.getInternalCounters().getCss():BigDecimal.ZERO;
		BigDecimal crs=p.getInternalCounters().getCrs()!=null?p.getInternalCounters().getCrs():BigDecimal.ZERO;
		
		return cs.add(css).subtract(crs);
	}


	private BigDecimal calculateQqta(Position p) {
		// QS+QSS-QRS
		BigDecimal qs=p.getInternalCounters().getQs()!=null?p.getInternalCounters().getQs():BigDecimal.ZERO;
		BigDecimal qss=p.getInternalCounters().getQss()!=null?p.getInternalCounters().getQss():BigDecimal.ZERO;
		BigDecimal qrs=p.getInternalCounters().getQrs()!=null?p.getInternalCounters().getQrs():BigDecimal.ZERO;
		
		return qs.add(qss).subtract(qrs);
	}


}
