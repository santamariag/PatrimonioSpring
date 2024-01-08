package it.poste.patrimonio.db.model;



import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.Data;

@Document(collection = "prices")
@Data
public class Price {
	
	@Id
	private String isin;
	@Version
	private Long version;
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal price;

}
