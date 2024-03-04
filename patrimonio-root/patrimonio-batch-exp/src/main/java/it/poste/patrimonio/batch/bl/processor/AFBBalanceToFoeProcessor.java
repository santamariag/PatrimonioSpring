package it.poste.patrimonio.batch.bl.processor;

import it.poste.patrimonio.bl.util.BusinessLogicUtil;
import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.db.repository.IFoeRepository;
import it.poste.patrimonio.itf.model.AFBBalanceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Slf4j
public class AFBBalanceToFoeProcessor implements ItemProcessor<AFBBalanceDTO, List<Foe>>{


	@Value("#{stepExecution}")
	private StepExecution stepExecution;
	
	private final IFoeRepository foeRepository;
	
	@Autowired
	private BusinessLogicUtil businessLogicUtil;
	
	
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
					p.getInternalCountersGpmFoe().setCs(item.getCtv());
					p.getInternalCountersGpmFoe().setQs(item.getQta());
					p.getDetail().setQqta(businessLogicUtil.calculateQqta(p));
					p.getDetail().setIvalbas(businessLogicUtil.calculateCtv(p));
					p.getDetail().setDulprz(item.getReferenceDate());
					p.getDetail().setIprzat(item.getPrice());
				}
			});
		});
		
		return foeList;	
		
	}

}
