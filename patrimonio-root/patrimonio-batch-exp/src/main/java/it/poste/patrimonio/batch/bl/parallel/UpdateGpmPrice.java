package it.poste.patrimonio.batch.bl.parallel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.google.common.collect.Range;

import it.poste.patrimonio.db.model.Gpm;
import it.poste.patrimonio.db.model.Position;
import it.poste.patrimonio.db.repository.IGpmRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpdateGpmPrice implements Callable<Result>{

	private IGpmRepository gpmRepository;

	private final Range<Long> pageRange;

	private final int pageSizeGpm;

	private final Map<String, BigDecimal> pricemap;
	
	private final Long id;
	


	public UpdateGpmPrice(Long id, Range<Long> pageRange, int pageSizeGpm, IGpmRepository gpmRepository, Map<String, BigDecimal> pricemap) {

		this.pageSizeGpm = pageSizeGpm;
		this.pageRange = pageRange;
		this.gpmRepository = gpmRepository;
		this.pricemap = pricemap;
		this.id = id;
	}



	@Override
	public Result call() throws Exception {

		for (Long page=pageRange.lowerEndpoint()-1; page<pageRange.upperEndpoint(); page++) {

			log.info("Thread {} Elaboro pagina {} isin {} price {} ", id,  page, pricemap.keySet(), pricemap.values());

			Pageable pageable = PageRequest.of(page.intValue(), pageSizeGpm, Sort.by(Direction.ASC, "ndg"));
			//Page<Gpm> gpmPage=gpmRepository.findByIsinInAndPriceNotIn(pricemap.keySet(), pricemap.values(), pageable);
			Page<Gpm> gpmPage=gpmRepository.findByIsinIn(pricemap.keySet(), pageable);
			log.info("Element in page "+gpmPage.getNumberOfElements());

			for(Gpm gpm : gpmPage.getContent()) {
				elaborateGpm(pricemap, gpm);
			}

		}
		return new Result(this.id.toString(), LocalDateTime.now().toString());
	}

	private void elaborateGpm(Map<String, BigDecimal> pricemap, Gpm gpm) {

		log.info("+++++++"+gpm);
		boolean modified=false;
		for(Position p : gpm.getPatrimonioOld().getPosizioni()) {
		//gpm.getPatrimonioOld().getPosizioni().forEach(p->{
			if(pricemap.containsKey(p.getDetail().getIsin())){
				BigDecimal newPrice=pricemap.get(p.getDetail().getIsin());
				if(p.getDetail().getIprzat().compareTo(newPrice)!=0) {
					p.getDetail().setIprzat(newPrice);
					p.getDetail().setIvalbas(p.getDetail().getIprzat().multiply(p.getDetail().getQqta()));
					modified=true;
				} else {
					log.info("La posizione con isin {} ha già lo stesso prezzo", p.getDetail().getIsin());
				}				
			}
		}//);
		if(modified)
			gpmRepository.saveGpm(gpm);
	}
}
