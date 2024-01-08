package it.poste.patrimonio.itf.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import it.poste.patrimonio.db.model.Event;
import it.poste.patrimonio.db.model.Gpm;
import it.poste.patrimonio.db.model.Patrimonio;
import it.poste.patrimonio.db.model.Position;
import it.poste.patrimonio.itf.model.EventDTO;
import it.poste.patrimonio.itf.model.GpmDTO;
import it.poste.patrimonio.itf.model.PatrimonioDTO;
import it.poste.patrimonio.rs.specs.model.DettaglioPatrimonioTypeTypeNs2;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring" )
public interface GpmMapper {
	
	@Mappings({
			@Mapping(source = "iprzat", target = "iprzat", qualifiedByName = "bigDecimalToString"),
			@Mapping(source = "qqta", target = "qqta", qualifiedByName = "longToString")
	})
	List<DettaglioPatrimonioTypeTypeNs2> modelToApi(List<Position> model);
	
	@Mappings({
			@Mapping(source = "iprzat", target = "iprzat", qualifiedByName = "stringToBigDecimal"),
			@Mapping(source = "qqta", target = "qqta", qualifiedByName = "stringToLong")
	})	
	List<Position> apiToModel(List<DettaglioPatrimonioTypeTypeNs2> api);
	
	@Named("stringToBigDecimal")
    default BigDecimal stringToBigDecimal(String value) {
       
		return new BigDecimal(value);
    }
	
	@Named("bigDecimalToString")
    default String bigDecimalToString(BigDecimal value) {
       
		return value.toString();
    }
	
	@Named("stringToLong")
    default Long stringToLong(String value) {
       
		return Long.valueOf(value);
    }
	
	@Named("longToString")
    default String longToString(Long value) {
       
		return value.toString();
    }
	
	GpmDTO modelToApi(Gpm gpm);
	
	Gpm apiToModel(GpmDTO gpm);
	
	PatrimonioDTO map(Patrimonio value);
	
	EventDTO map(Event value);
	
	Patrimonio map(PatrimonioDTO value);
	
	Event map(EventDTO value);
	

}