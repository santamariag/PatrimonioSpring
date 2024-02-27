package it.poste.patrimonio.db.model.common;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.Data;

@Data
public class Detail {
	
	private String ndg;
	private String idBisogno;
	private String descrizioneBisogno;
	private String idFamigliaProdotto;
	private String descrizioneFamigliaProdotto;
	private String nrap;  //deposit/number
	private String cstrfin; //prodotto (cod linea)
	private String xstrfin; //descrizione prodotto da recuperare tramite servizio
	private String csdp; //deposit/index
	private String cndgs;
	private String trap; //tipo rapporto (GPM/FOE/TIT) su base ist (1/5)
	private String strp;
	private String xint; //descrizione cliente customerDescr
	private LocalDate dtaper;
	private String xblc;
	private String cage; //deposit/agency
	private String xstt;
	private String tprm; // tipo titolo
	private LocalDate ddec; //data corrente
	private LocalDate dsca;
	private String isin;
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal qqta; //Quantità patrimonio
	private LocalDate dreg;
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal iprzat; //Prezzo
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal icamat;
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal ivalbas;  //Controvalore patrimonio
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal qqtavin;
	private String cdivemi; //Divisa
	private String cdivtrz;
	private String fpac; //Presenza PAC (S se cpac>0/N altrimenti)
	private LocalDate dulprz; //data ultimo prezzo
	private LocalDate dultacq; //data ultimo acquisto
	private LocalDate dulcam; 
	private String ccdrreg;
	private String fese;
	private PeseX peseX;
	private String cndgc;
	private String ictvRisX;
	private String idProd;  //ID prodotto

}
