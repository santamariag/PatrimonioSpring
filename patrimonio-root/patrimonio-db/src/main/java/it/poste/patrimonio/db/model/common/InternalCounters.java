package it.poste.patrimonio.db.model.common;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.Data;

@Data
public class InternalCounters {
	
	private Long cpac; //Contatore PAC stato(presenza pac- 0,1=+1 in insert; 2,3=-1 in update)
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal cs = BigDecimal.ZERO;  //Controvalore SGR(CS)
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal qs = BigDecimal.ZERO;  //Quantità SGR(QS)
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal qtaint = BigDecimal.ZERO;  //Quantità interna
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal css = BigDecimal.ZERO;  //Controvalore sottoscrizioni sospese(CSS)
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal qss = BigDecimal.ZERO; //Quantità sottoscrizioni sospese(QSS)
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal crs = BigDecimal.ZERO;  //Controvalore rimborsi sospesi(CRS)
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal qrs = BigDecimal.ZERO;  //Quantità rimborsi sospesi(QRS)

}
