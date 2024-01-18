package it.poste.patrimonio.itf.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class DetailDTO {
	
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
	private LocalDate dtaper;
	private String xblc;
	private String cage;
	private String xstt;
	private String tprm;
	private LocalDate ddec;
	private LocalDate dsca;
	private String isin;
	private BigDecimal qqta;
	private LocalDate dreg;
	private BigDecimal iprzat;
	private BigDecimal icamat;
	private BigDecimal ivalbas;
	private BigDecimal qqtavin;
	private String cdivemi;
	private String cdivtrz;
	private String fpac;
	private LocalDate dulprz;
	private LocalDate dultacq;
	private LocalDate dulcam;
	private String ccdrreg;
	private String fese;
	private PeseXDTO peseX;
	private String cndgc;
	private String ictvRisX;
	private String idProd;

}
