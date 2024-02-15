package it.poste.patrimonio.bl.service;

import it.poste.patrimonio.event.business.model.gpmfoe.PacCreation;
import it.poste.patrimonio.event.business.model.gpmfoe.PacDelete;

public interface IPACService {

    void pacCreation(PacCreation dto);

    void pacDelete(PacDelete dto);
}
