package it.poste.patrimonio.bl.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.poste.patrimonio.db.constants.Status;
import it.poste.patrimonio.db.model.common.PatrimonioOld;
import it.poste.patrimonio.db.model.CommonDocument;
import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.db.model.Gpm;
import it.poste.patrimonio.db.repository.IFoeRepository;
import it.poste.patrimonio.db.repository.IGpmRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.poste.patrimonio.db.model.common.Position;


@Slf4j
@Component
public class BusinessLogicUtil {

    @Autowired
    IFoeRepository foeRepository;
    @Autowired
    IGpmRepository gpmRepository;

    public BigDecimal calculateCtv(Position p) {
        //CS+CSS-CRS
        BigDecimal cs = p.getInternalCountersGpmFoe().getCs() != null ? p.getInternalCountersGpmFoe().getCs() : BigDecimal.ZERO;
        BigDecimal css = p.getInternalCountersGpmFoe().getCss() != null ? p.getInternalCountersGpmFoe().getCss() : BigDecimal.ZERO;
        BigDecimal crs = p.getInternalCountersGpmFoe().getCrs() != null ? p.getInternalCountersGpmFoe().getCrs() : BigDecimal.ZERO;

        return cs.add(css).subtract(crs);
    }


    public BigDecimal calculateQqta(Position p) {
        // QS+QSS-QRS
        BigDecimal qs = p.getInternalCountersGpmFoe().getQs() != null ? p.getInternalCountersGpmFoe().getQs() : BigDecimal.ZERO;
        BigDecimal qss = p.getInternalCountersGpmFoe().getQss() != null ? p.getInternalCountersGpmFoe().getQss() : BigDecimal.ZERO;
        BigDecimal qrs = p.getInternalCountersGpmFoe().getQrs() != null ? p.getInternalCountersGpmFoe().getQrs() : BigDecimal.ZERO;

        return qs.add(qss).subtract(qrs);
    }

    public CommonDocument findDataByNdg(String institute, String ndg) {
        if (institute != null && ndg != null) {
            switch (institute) {
                case Constants.GPM_INST: {
                    List<Gpm> gpms = gpmRepository.findByNdgIn(Collections.singletonList(ndg));
                    if (!gpms.isEmpty()) {
                        log.info("BusinessLogicUtil.findDataByNdg - Gpm Document found");
                        return gpms.get(0);
                    }
                    break;
                }
                case Constants.FOE_INST: {
                    List<Foe> foes = foeRepository.findByNdgIn(Collections.singletonList(ndg));
                    if (!foes.isEmpty()) {
                        log.info("BusinessLogicUtil.findDataByNdg - Foe Document found");
                        return foes.get(0);
                    }
                    break;
                }
            }
        }
        return null;

    }

    //In assenza di dati:
    //    creare cliente tramide ndg
    //    mettre complete a false
    //    creare lista posizioni
    public CommonDocument findOrCreateDocumentByNdg(String institute, String ndg) {
        log.info("BusinessLogicUtil.findOrCreateDocumentByNdg - Looking for document with institute "
                + institute + " and ndg " + ndg);
        CommonDocument data = findDataByNdg(institute, ndg);
        if (data == null) {
            log.info("BusinessLogicUtil.findOrCreateDocumentByNdg - Document not found, generating new document");
            data = new CommonDocument();
            data.setNdg(ndg);
            data.setComplete(false);
            data.setPatrimonioOld(new PatrimonioOld());
            data.getPatrimonioOld().setPosizioni(new ArrayList<>());
        }

        return data;
    }

    public void saveData(String institute, CommonDocument data) {

        switch (institute) {
            case Constants.GPM_INST: {
                log.info("BusinessLogicUtil.saveData - Saving Gpm Document");
                gpmRepository.save((Gpm) data);
                break;
            }
            case Constants.FOE_INST: {
                log.info("BusinessLogicUtil.saveData - Saving Foe Document");
                foeRepository.save((Foe) data);
                break;
            }
        }
    }

    public void deleteData(String institute, CommonDocument data) {

        switch (institute) {
            case Constants.GPM_INST: {
                log.info("BusinessLogicUtil.deleteData - Deleting Gpm Document");
                gpmRepository.delete((Gpm) data);
                break;
            }
            case Constants.FOE_INST: {
                log.info("BusinessLogicUtil.deleteData - Deleting Gpm Document");
                foeRepository.delete((Foe) data);
                break;
            }
        }
    }
    public Status checkStatus(String status){
        if(status.equals(Constants.ACTIVE_CHECK)){
                return Status.ACTIVE;
            }
        if (Constants.BLOCKED_CHECK.contains(status)){
            return Status.BLOCKED;
        }
        if (Constants.CLOSED_CHECK.contains(status)){
            return Status.CLOSED;
        }
        return Status.ACTIVE;
    }
}