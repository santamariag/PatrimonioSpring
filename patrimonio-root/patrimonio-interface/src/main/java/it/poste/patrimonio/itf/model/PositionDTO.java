package it.poste.patrimonio.itf.model;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.Data;

@Data
public class PositionDTO {
	
	private String ndg;
	private String idBisogno;
	private String descrizioneBisogno;
	private String idFamigliaProdotto;
	private String descrizioneFamigliaProdotto;
	private String nrap;
	private String cstrfin;
	private String xstrfin;
	private String csdp;
	private String cndgs;
	private String trap;
	private String strp;
	private String xint;
	private String dtaper;
	private String xblc;
	private String cage;
	private String xstt;
	private String tprm;
	private String ddec;
	private String dsca;
	private String isin;
	//@Field(targetType = FieldType.DECIMAL128) 
	private Long qqta;
	private String dreg;
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal iprzat;
	private String icamat;
	@Field(targetType = FieldType.DECIMAL128) 
	private BigDecimal ivalbas;
	private String qqtavin;
	private String cdivemi;
	private String cdivtrz;
	private String fpac;
	private String dulprz;
	private String dultacq;
	private String dulcam;
	private String ccdrreg;
	private String fese;
	private PeseXDTO peseX;
	private String cndgc;
	private String ictvRisX;
	private String idProd;


}