package it.poste.patrimonio.event.business.impl.titoli;

import it.poste.patrimonio.event.business.model.ITitoliBusinessEvent;
import lombok.Data;

@Data
public class RprzEvent implements ITitoliBusinessEvent {
    String target;
    @Override
    public String getKafkaKey() {
        return null;
    }
}
