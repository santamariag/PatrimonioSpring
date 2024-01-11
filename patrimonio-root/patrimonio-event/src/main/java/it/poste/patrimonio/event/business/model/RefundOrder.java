package it.poste.patrimonio.event.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefundOrder {
	
	private String ndg;
	private String clientIntCode; //codice interno cliente
	private String institute; //(tipo rapporto 1 GPM, 5 FOE)
	private String productCode;
	private String productId;
	private String refundType;
	private String reason; // causale
	private String flagMfM;
	private String status;
	private String nomMov;
	private String ctvMov;
}
