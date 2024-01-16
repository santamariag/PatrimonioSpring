package it.poste.patrimonio.db.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.Data;

/**
 * Mapping for GPM/FOE
 * @author santamariag
 * TODO Titoli
 */
@Data
public class Position {
	
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
	private String trap; //tipo rapporto (GPM/FOE) su base ist (1/5)
	private String strp;
	private String xint; //descrizione cliente customerDescr
	private String dtaper;
	private String xblc;
	private String cage; //deposit/agency
	private String xstt;
	private String tprm;
	private String ddec; //data corrente
	private String dsca;
	private String isin;
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal qqta; //Quantità patrimonio
	private String dreg;
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal iprzat; //Prezzo
	private String icamat;
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal ivalbas;  //Controvalore patrimonio
	private String qqtavin;
	private String cdivemi; //Divisa
	private String cdivtrz;
	private String fpac; //Presenza PAC (S/N)
	private LocalDate dulprz; //data ultimo prezzo
	private LocalDate dultacq; //data ultimo acquisto
	private LocalDate dulcam; 
	private String ccdrreg;
	private String fese;
	private PeseX peseX;
	private String cndgc;
	private String ictvRisX;
	private String idProd;  //ID prodotto
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
