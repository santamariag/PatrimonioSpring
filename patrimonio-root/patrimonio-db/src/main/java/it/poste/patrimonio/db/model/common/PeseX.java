package it.poste.patrimonio.db.model.common;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.Data;

@Data
public class PeseX {
	
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal pese;

}
