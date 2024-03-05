package it.poste.patrimonio.bl.service;

import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.event.business.impl.gpmfoe.AFBItem;
import it.poste.patrimonio.event.business.impl.gpmfoe.MFMItem;
import it.poste.patrimonio.itf.model.FoeDTO;
import it.poste.patrimonio.rs.specs.model.PatrimonioClienteOutputElementNs1;

import java.util.List;


public interface IGpmFoeService {
    void updateFoe(AFBItem message);

    void updateGpm(MFMItem message);
}
