package it.poste.patrimonio.event.business.impl.titoli;

import it.poste.patrimonio.event.business.model.ITitoliBusinessEvent;
import lombok.Data;

@Data
public class DepositEvent implements ITitoliBusinessEvent {

    private String branch; // filiale
    private String agency; // agenzia
    private String doss; // numero
    private String index; // rubrica

    @Override
    public String getKafkaKey() {
        return branch+agency+doss+index;
    }

    private String ndg;

}
