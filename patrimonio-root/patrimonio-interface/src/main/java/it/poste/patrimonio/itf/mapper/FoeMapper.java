package it.poste.patrimonio.itf.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.db.model.Patrimonio;
import it.poste.patrimonio.db.model.PeseX;
import it.poste.patrimonio.db.model.Position;
import it.poste.patrimonio.itf.model.FoeDTO;
import it.poste.patrimonio.itf.model.PatrimonioDTO;
import it.poste.patrimonio.rs.specs.model.DettaglioPatrimonioTypeTypeNs2;
import it.poste.patrimonio.rs.specs.model.EsitoTypeTypeNs2Nil;
import it.poste.patrimonio.rs.specs.model.PeseXTypeTypeNs2;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, 
		nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS , 
		componentModel = "spring")
public interface FoeMapper {
	
	@Mappings({
		@Mapping(source = "detail", target = "."),
		@Mapping(source = "detail.qqta", target = "qqta", qualifiedByName = "bigDecimalToStringScale3" ),
		@Mapping(source = "detail.iprzat", target = "iprzat", qualifiedByName = "bigDecimalToStringScale6"),
		@Mapping(source = "detail.icamat", target = "icamat", qualifiedByName = "bigDecimalToStringScale6"),
		@Mapping(source = "detail.ivalbas", target = "ivalbas", qualifiedByName = "bigDecimalToStringScale3"),
		@Mapping(source = "detail.qqtavin", target = "qqtavin", qualifiedByName = "bigDecimalToStringScale3"),			
		@Mapping(source = "detail.dulprz", target = "dulprz", dateFormat = "yyyyMMdd"),
		@Mapping(source = "detail.dulcam", target = "dulcam", dateFormat = "yyyyMMdd")

	})	
	DettaglioPatrimonioTypeTypeNs2 modelToApi(Position model);
	
	@Mappings({
		@Mapping(source = "pese", target = "pese", qualifiedByName = "bigDecimalToStringScale3" ),
	})	
	PeseXTypeTypeNs2 modelToApi(PeseX model);
	
	List<DettaglioPatrimonioTypeTypeNs2> modelListToApiList(List<Position> model);
	
	List<Position> apiListToModelList(List<DettaglioPatrimonioTypeTypeNs2> api);
	
	@AfterMapping
    default void afterMapping(@MappingTarget DettaglioPatrimonioTypeTypeNs2 patrimonio, Position position) {
        
		EsitoTypeTypeNs2Nil esito=new EsitoTypeTypeNs2Nil();
		esito.setEsito("OK");
		esito.setDettaglioErrore(new ArrayList<>());
		patrimonio.setEsitoStructure(esito);
    }    
	
	
	@Named("bigDecimalToStringScale3")
    default String bigDecimalToStringScale3(BigDecimal value) {
       
		return value.setScale(3).toString();
    }
	
	@Named("bigDecimalToStringScale6")
    default String bigDecimalToStringScale6(BigDecimal value) {
       
		return value.setScale(6).toString();
    }
	
	FoeDTO modelToApi(Foe foe);
	
	Foe apiToModel(FoeDTO foe);
	
	PatrimonioDTO map(Patrimonio value);
		
	Patrimonio map(PatrimonioDTO value);	

}
