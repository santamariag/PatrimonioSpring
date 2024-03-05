package it.poste.patrimonio.bl.service;

import it.poste.patrimonio.event.business.impl.gpmfoe.AfbEvent;
import it.poste.patrimonio.event.business.impl.gpmfoe.MfmEvent;


public interface IGpmFoeService {
    void updateFoe(AfbEvent message);

    void updateGpm(MfmEvent message);
}
