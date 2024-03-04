package it.poste.patrimonio.db.model.titoli;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InternalCountersTitoli {

	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal qp = BigDecimal.ZERO;  //quantità precedente
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal d = BigDecimal.ZERO;  //divisore
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal ad = BigDecimal.ZERO;  //addendum

	private LocalDate qpdta; //Data quantità precedente
	private LocalDate addta; //Data addendum

}
