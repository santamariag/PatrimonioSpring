package it.poste.patrimonio.itf.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AFBBalanceDTO {
	
	private String fiscalCode;
	private String productId;
	private BigDecimal qta;
	private BigDecimal ctv;
	private String product;
	private String branch;
	private String agency;
	private String number;
	private BigDecimal price;
	private String referenceDate;
}
