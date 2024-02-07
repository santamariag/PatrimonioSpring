package it.poste.patrimonio.bl.service.impl;

import it.poste.patrimonio.bl.service.IPACService;
import it.poste.patrimonio.bl.util.Constants;
import it.poste.patrimonio.bl.util.PositionUtil;
import it.poste.patrimonio.db.model.*;
import it.poste.patrimonio.event.business.model.PacCreation;
import it.poste.patrimonio.event.business.model.PacDelete;

public class PACService implements IPACService {

    PositionUtil util;

    @Override
    public void pacCreation(PacCreation dto) {
        //Prende record tramite ndg e scorre posizioni cercando corrispondenza del prodotto e fpac in detail s o n poi cpac +1
        CommonData data = util.findDataByNdg(dto.getInstitute(), dto.getNdg());

        for (Position pos : data.getPatrimonioOld().getPosizioni()){
            if (pos.getDetail().getIdProd().equals(dto.getProductId())){
                Long cpac = pos.getInternalCounters().getCpac();
                pos.getInternalCounters().setCpac(cpac + 1);
                if (pos.getInternalCounters().getCpac() > 0) {
                    pos.getDetail().setFpac(Constants.Y);
                }
                break;
            }
        }

        util.saveData(dto.getInstitute(), data);

    }

    @Override
    public void pacDelete(PacDelete dto) {
        CommonData data = util.findDataByNdg(dto.getInstitute(), dto.getNdg());

        for (Position pos : data.getPatrimonioOld().getPosizioni()){
            if (pos.getDetail().getIdProd().equals(dto.getProductId())){
                Long cpac = pos.getInternalCounters().getCpac();
                pos.getInternalCounters().setCpac(cpac - 1);
                if (pos.getInternalCounters().getCpac() < 1) {
                    pos.getDetail().setFpac(Constants.N);
                }
                break;
            }
        }

        util.saveData(dto.getInstitute(), data);
    }


}
