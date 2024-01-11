package it.poste.patrimonio.itf.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MFMBalanceDTO {
	
	private String fiscalCode;
	private String productId;
	private BigDecimal qtaSub;
	private BigDecimal qtaRef;
	private BigDecimal ctv;
	private String product;
	
}
