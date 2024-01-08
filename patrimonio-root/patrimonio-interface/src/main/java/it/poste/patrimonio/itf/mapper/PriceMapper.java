package it.poste.patrimonio.itf.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import it.poste.patrimonio.db.model.Price;
import it.poste.patrimonio.itf.model.PriceDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring" )
public interface PriceMapper {
	
	PriceDTO modelToApi(Price model);
	Price apiToModel(PriceDTO api);


}
