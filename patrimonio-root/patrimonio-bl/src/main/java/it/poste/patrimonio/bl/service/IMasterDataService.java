package it.poste.patrimonio.bl.service;

import it.poste.patrimonio.event.business.impl.gpmfoe.MasterDataCreation;
import it.poste.patrimonio.event.business.impl.gpmfoe.MasterDataDelete;
import it.poste.patrimonio.event.business.impl.gpmfoe.MasterDataLock;

public interface IMasterDataService {
    void masterDataCreation(MasterDataCreation dto);
    void masterDataDelete(MasterDataDelete dto);
    void masterDataLock(MasterDataLock dto);
}
