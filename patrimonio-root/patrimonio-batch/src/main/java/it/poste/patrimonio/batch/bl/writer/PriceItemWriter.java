package it.poste.patrimonio.batch.bl.writer;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemWriter;
import org.springframework.transaction.annotation.Transactional;

import it.poste.patrimonio.db.model.Gpm;
import it.poste.patrimonio.db.model.Price;
import it.poste.patrimonio.db.repository.IGpmRepository;
import it.poste.patrimonio.db.repository.IPriceRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriceItemWriter implements ItemWriter<Price>{
	

    private final IPriceRepository priceRepository;
    
    private final IGpmRepository gpmRepository;

    public PriceItemWriter(IPriceRepository priceRepository, IGpmRepository gpmRepository) {
        this.priceRepository = priceRepository;
		this.gpmRepository = gpmRepository;
    }

    @Override
    //@Transactional
    public void write(List<? extends Price> priceList) throws Exception {
    	
    	
        log.info("Saving {} prices objects", priceList.size());
        priceRepository.saveAll(priceList);
        
        Map<String, BigDecimal> pricemap=priceList.stream().collect(Collectors.toMap(Price::getIsin, Price::getPrice));
       
        Iterator<Gpm> it=gpmRepository.findByIsinIn(pricemap.keySet()).iterator();
        //List<Gpm> gpmToUpdate=new ArrayList<>();
        
        while(it.hasNext()) {
        	
        	Gpm next=it.next();
        	log.info("GPM "+next);
        	next.getPatrimonioOld().getPosizioni().forEach(p->{
        		if(pricemap.containsKey(p.getIsin())){
        			p.setIprzat(pricemap.get(p.getIsin()));
        			p.setIvalbas(p.getIprzat().multiply(new BigDecimal(p.getQqta())));
        			//gpmToUpdate.add(next);
        			gpmRepository.saveGpm(next);
        		}
        	});
        	//throw new RuntimeException("Test");
        } 

        //gpmRepository.saveAllGpm(gpmToUpdate);

    }
}

