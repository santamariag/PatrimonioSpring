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
public class RefundOrder implements IGpmFoeBusinessEvent {
	
	private String ndg;
	private String clientIntCode; //codice interno cliente
	private String institute; //(tipo rapporto 1 GPM, 5 FOE)
	private String productCode;
	private String productId;
	private String refundType;
	private String reason; // causale
	private String flagMfM;
	private String status;
	private BigDecimal nomMov;
	private BigDecimal ctvMov;

	@Override
	public String getKey() {
		return getInstitute() + "#" + getNdg();
	}

}
