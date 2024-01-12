package it.poste.patrimonio.itf.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import it.poste.patrimonio.db.model.Price;
import it.poste.patrimonio.itf.model.PriceDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, 
		nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS , 
		componentModel = "spring")
public interface PriceMapper {
	
	PriceDTO modelToApi(Price model);
	Price apiToModel(PriceDTO api);


}
