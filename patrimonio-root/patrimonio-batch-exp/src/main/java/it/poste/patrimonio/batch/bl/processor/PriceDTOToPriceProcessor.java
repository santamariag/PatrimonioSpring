package it.poste.patrimonio.batch.bl.processor;

import java.util.Optional;

import org.springframework.batch.item.ItemProcessor;

import it.poste.patrimonio.db.model.Price;
import it.poste.patrimonio.db.repository.IPriceRepository;
import it.poste.patrimonio.itf.mapper.PriceMapper;
import it.poste.patrimonio.itf.model.PriceDTO;

public class PriceDTOToPriceProcessor implements ItemProcessor<PriceDTO, Price>{

	private final PriceMapper mapper;
	
	private final IPriceRepository priceRepository;
	
	
	public PriceDTOToPriceProcessor(PriceMapper mapper, IPriceRepository priceRepository) {
		this.mapper = mapper;
		this.priceRepository = priceRepository;
	}


	@Override
	public Price process(PriceDTO item) throws Exception {
		
		/*if(priceRepository.findByIsinAndPrice(item.getIsin(), item.getPrice()).isEmpty())
			
			return mapper.apiToModel(item);*/
		
		Optional<Price> priceOpt=priceRepository.findByIsin(item.getIsin());
		
		if(priceOpt.isEmpty())
			return mapper.apiToModel(item);
		
		Price price=priceOpt.get();
		
		if(!price.getPrice().equals(item.getPrice())) {
			price.setPrice(item.getPrice());
			return price;
		}
		return null;
		
		
		
	}

}
