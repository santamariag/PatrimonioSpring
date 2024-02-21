package it.poste.patrimonio.event.business.impl.titoli;

import it.poste.patrimonio.event.business.model.ITitoliBusinessEvent;
import lombok.Data;

@Data
public class TestTitoliEvent implements ITitoliBusinessEvent {

    String key;

}
