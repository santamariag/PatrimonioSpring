package it.poste.patrimonio.itf.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class InternalCountersDTO {
	
	private Long cpac;
	private BigDecimal cs;
	private BigDecimal qs;
	private BigDecimal qtaint;
	private BigDecimal css;
	private BigDecimal qss;
	private BigDecimal crs;
	private BigDecimal qrs;

}
