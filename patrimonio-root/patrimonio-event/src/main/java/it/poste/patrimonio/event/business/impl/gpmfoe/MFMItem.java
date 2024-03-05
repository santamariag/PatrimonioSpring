package it.poste.patrimonio.event.business.impl.gpmfoe;

import it.poste.patrimonio.event.business.model.IGpmFoeBusinessEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MFMItem implements IGpmFoeBusinessEvent {
    private String ndg;
    private String fiscalCode;
    private String productId;
    private BigDecimal qtaSub;
    private BigDecimal qtaRef;
    private BigDecimal ctv;
    private String product;

    @Override
    public String getKey() {
        return "1" + "#" + ndg;
    }
}

