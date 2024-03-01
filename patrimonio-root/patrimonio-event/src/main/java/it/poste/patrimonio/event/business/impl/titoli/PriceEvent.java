package it.poste.patrimonio.event.business.impl.titoli;

import it.poste.patrimonio.event.business.model.Consts;
import it.poste.patrimonio.event.business.model.ITitoliBusinessEvent;
import lombok.Data;

@Data
public class PriceEvent implements ITitoliBusinessEvent {

    private String isin;
    private String branch; // filiale
    private String agency; // agenzia
    private String doss; // numero
    private String index; // rubrica

    String target; // "POSITION/FINANCIAL_INSTRUMENT"

    @Override
    public String getKafkaKey() {
        if ( Consts.EV_TARGET_POSITION.equals(target) ) {
            return branch+agency+doss+index;
        }else{
            return isin;
        }
    }


}
