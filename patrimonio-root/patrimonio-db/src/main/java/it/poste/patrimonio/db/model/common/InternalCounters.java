package it.poste.patrimonio.db.model.common;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.Data;

@Data
public class InternalCounters {
	
	private Long cpac; //Contatore PAC stato(presenza pac- 0,1=+1 in insert; 2,3=-1 in update)
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal cs;  //Controvalore SGR(CS)
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal qs;  //Quantità SGR(QS)
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal qtaint;  //Quantità interna
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal css;  //Controvalore sottoscrizioni sospese(CSS)
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal qss; //Quantità sottoscrizioni sospese(QSS)
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal crs;  //Controvalore rimborsi sospesi(CRS)
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal qrs;  //Quantità rimborsi sospesi(QRS)

}
