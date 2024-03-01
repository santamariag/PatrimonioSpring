package it.poste.patrimonio.event.business.impl.gpmfoe;

import it.poste.patrimonio.event.business.model.IGpmFoeBusinessEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionOrder implements IGpmFoeBusinessEvent {
	
	private String ndg;
	private String clientIntCode; //codice interno cliente
	private String institute; // (tipo rapporto 1 GPM, 5 FOE)
	private String productCode;
	private String productId;
	private String reason; // causale
	private String flagMfM;
	private String status;
	private String productDesc;
	private String currency;
	private BigDecimal nomMov;
	private BigDecimal ctvMov;

	@Override
	public String getKey() { return institute + "#" + ndg; }

}
