package it.poste.patrimonio.bl.service;

import it.poste.patrimonio.event.business.model.gpmfoe.MasterDataCreation;
import it.poste.patrimonio.event.business.model.gpmfoe.MasterDataDelete;
import it.poste.patrimonio.event.business.model.gpmfoe.MasterDataLock;

public interface IMasterDataService {
    void masterDataCreation(MasterDataCreation dto);
    void masterDataDelete(MasterDataDelete dto);
    void masterDataLock(MasterDataLock dto);
}
