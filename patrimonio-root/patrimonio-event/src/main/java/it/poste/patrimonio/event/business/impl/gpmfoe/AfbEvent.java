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
public class AfbEvent implements IGpmFoeBusinessEvent {
    private String ndg;
    private String branch;
    private String agency;
    private String number;
    private String index;
    private String product;
    private String productId;
    private BigDecimal qta;
    private LocalDate referenceDate;
    private BigDecimal ctv;
    private BigDecimal price;

    @Override
    public String getKey() {
        return "5" + "#" + ndg;
    }
}

