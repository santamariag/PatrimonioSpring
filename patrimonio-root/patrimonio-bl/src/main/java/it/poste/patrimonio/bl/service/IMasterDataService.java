package it.poste.patrimonio.bl.service;

import it.poste.patrimonio.event.business.model.MasterDataCreation;
import it.poste.patrimonio.event.business.model.MasterDataDelete;
import it.poste.patrimonio.event.business.model.MasterDataLock;

public interface IMasterDataService {
    void masterDataCreation(MasterDataCreation dto);
    void masterDataDelete(MasterDataDelete dto);
    void masterDataLock(MasterDataLock dto);
}
