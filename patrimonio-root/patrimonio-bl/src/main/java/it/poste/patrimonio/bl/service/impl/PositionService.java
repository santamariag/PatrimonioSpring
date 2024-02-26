package it.poste.patrimonio.bl.service.impl;

import it.poste.patrimonio.bl.service.IPositionService;
import it.poste.patrimonio.bl.util.Constants;
import it.poste.patrimonio.bl.util.BusinessLogicUtil;
import it.poste.patrimonio.db.model.common.Detail;
import it.poste.patrimonio.db.model.common.InternalCounters;
import it.poste.patrimonio.db.model.common.Position;
import it.poste.patrimonio.db.model.CommonDocument;
import it.poste.patrimonio.event.business.impl.gpmfoe.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static it.poste.patrimonio.bl.util.Constants.*;

@Slf4j
@Service
public class PositionService implements IPositionService {

    BusinessLogicUtil util;

    //- in assenza di dati creare data tramite ndg e posizione tramite id prodotto
    //- non risponde alle condizioni dello status -> non fa niente

    private Position findOrCreatePosition(CommonDocument data, String idProd, String product) {
        log.info("PositionService.findOrCreatePosition - Looking for position with id product " + idProd + " and product code " + product);
        List<Position> positions = data.getPatrimonioOld().getPosizioni();
        for (Position pos : positions) {
            if (pos.getDetail().getIdProd().equals(idProd) && pos.getDetail().getCstrfin().equals(product)) {
                log.info("PositionService.findOrCreatePosition - Position found");
                return pos;
            }
        }
        log.info("PositionService.findOrCreatePosition - Position not found, generating new position");
        Detail detail = new Detail();
        detail.setIdProd(idProd);
        detail.setCstrfin(product);

        return new Position(detail);
    }

    private void updateQtaAndCtv(Position pos, InternalCounters counters) {
        //quantità patrimonio: QS+QSS-QRS
        pos.getDetail().setQqta((counters.getQs().add(counters.getQss()).subtract(counters.getQrs())));
        //controvalore patrimonio: CS+CSS-CRS
        pos.getDetail().setIvalbas((counters.getCs().add(counters.getCss())).subtract(counters.getCrs()));
        log.info("PositionService.updateQtaAndCtv - Counters updated");
    }

    //7
    @Override
    public void insertSubOrder(SubscriptionOrder dto) {
        //- se inserimento aggiornare prodotto, id prodotto, descrizione prodotto (da Mifid), data primo movimento
        CommonDocument data = util.findOrCreateDocumentByNdg(dto.getInstitute(), dto.getNdg());

        Position pos = findOrCreatePosition(data, dto.getProductId(), dto.getProductCode());
        //detail.setXstrfin(chiamata a servizio esterno); TODO non abbiamo ancora il servizio
        pos.getDetail().setDdec(LocalDate.now());
        pos.getDetail().setCdivemi(dto.getCurrency());
        log.info("PositionService.insertSubOrder - Detail updated");

        //Aggiornare qtaInt, css, qss
        //se flag-mfm = s e codice stato = ese:
        // - quantità interna += nominale
        //se flag-mfm <> s e codice stato = ins:
        //- CSS += ctv mov
        //- QSS += nominale
        InternalCounters counters = pos.getInternalCounters();
        if (Constants.Y.equals(dto.getFlagMfM())) {
            if (STATUS_ESE.equals(dto.getStatus())) {
                log.info("PositionService.insertSubOrder - Updating qtaInt");
                counters.setQtaint(counters.getQtaint().add(dto.getNomMov()));
                pos.getDetail().setQqta(BigDecimal.ZERO); //(p)
                pos.getDetail().setIvalbas(BigDecimal.ZERO); //(p)
            }
        } else if (STATUS_INS.equals(dto.getStatus())) {
            log.info("PositionService.insertSubOrder - Updating css and qss");
            counters.setCss(counters.getCss().add(dto.getCtvMov()));
            counters.setQss(counters.getQss().add(dto.getNomMov()));
        }

        updateQtaAndCtv(pos, counters);

        util.saveData(dto.getInstitute(), data);
    }

    //8
    @Override
    public void subOrderFeedback(SubscriptionFeedback dto) {

        CommonDocument data = util.findOrCreateDocumentByNdg(dto.getInstitute(), dto.getNdg());
        if (data != null) {
            Position pos = findOrCreatePosition(data, dto.getProductId(), dto.getProductCode());
            //se flag-mfm <> s e codice stato = ese:
            //- quantità interna = + nominale
            //- CSS = - "ctv-cos"
            //- QSS = - nominale
            InternalCounters counters = pos.getInternalCounters();
            if (!Constants.Y.equals(dto.getFlagMfM()) && STATUS_ESE.equals(dto.getStatus())) {
                log.info("PositionService.subOrderFeedback - Updating qtaInt, css, and qss");
                counters.setQtaint(counters.getQtaint().add(dto.getNomMov()));
                pos.getDetail().setQqta(BigDecimal.ZERO); //(p)
                pos.getDetail().setIvalbas(BigDecimal.ZERO); //(p)
                counters.setCss(counters.getCss().subtract(dto.getCtvCos()));
                counters.setQss(counters.getQss().subtract(dto.getNomMov()));
            }

            updateQtaAndCtv(pos, counters);

            util.saveData(dto.getInstitute(), data);
        }
    }

    //9
    @Override
    public void insertRefundOrder(RefundOrder dto) {

        CommonDocument data = util.findOrCreateDocumentByNdg(dto.getInstitute(), dto.getNdg());
        if (data != null) {
            Position pos = findOrCreatePosition(data, dto.getProductId(), dto.getProductCode());
            //Aggiornare qtaInt, css, qss
            //se flag-mfm = s e codice stato = ese:
            //- quantità interna -= nominale
            //se flag-mfm <> s e codice stato = ins:
            //- CRS += ctv mov
            //- QRS += nominale
            InternalCounters counters = pos.getInternalCounters();
            if (Constants.Y.equals(dto.getFlagMfM())) {
                if (STATUS_ESE.equals(dto.getStatus())) {
                    log.info("PositionService.insertRefundOrder - Updating qtaInt");
                    counters.setQtaint(counters.getQtaint().subtract(dto.getNomMov()));
                    pos.getDetail().setQqta(BigDecimal.ZERO); //(p)
                    pos.getDetail().setIvalbas(BigDecimal.ZERO); //(p)
                }
            } else if (STATUS_INS.equals(dto.getStatus())) {
                log.info("PositionService.insertRefundOrder - Updating crs and qrs");
                counters.setCrs(counters.getCrs().add(dto.getCtvMov()));
                counters.setQrs(counters.getQrs().add(dto.getNomMov()));
            }

            //Indicazione rimborso totale (solo per GPM): se flag tipo rimb = t impostare a s altrimenti spazio
            if ("t".equals(dto.getRefundType())) {
                //TODO non è ancora chiaro che variabile deve aggiornare (m)
            }

            updateQtaAndCtv(pos, counters);

            util.saveData(dto.getInstitute(), data);
        }
    }

    //10
    @Override
    public void refundOrderFeedback(RefundFeedback dto) {

        CommonDocument data = util.findOrCreateDocumentByNdg(dto.getInstitute(), dto.getNdg());
        if (data != null) {
            Position pos = findOrCreatePosition(data, dto.getProductId(), dto.getProductCode());
            //se flag-mfm <> s e codice stato = ese:
            //- quantità interna -= nominale
            //- CRS -= ctv-cos
            //- QRS -= nominale

            InternalCounters counters = pos.getInternalCounters();
            if (!Constants.Y.equals(dto.getFlagMfM()) && STATUS_ESE.equals(dto.getStatus())) {
                log.info("PositionService.refundOrderFeedback - Updating qtaInt, crs, and qrs");
                counters.setQtaint(counters.getQtaint().subtract(dto.getNomMov()));
                pos.getDetail().setQqta(BigDecimal.ZERO); //(p)
                pos.getDetail().setIvalbas(BigDecimal.ZERO); //(p)
                counters.setCrs(counters.getCrs().subtract(dto.getCtvCos()));
                counters.setQrs(counters.getQrs().subtract(dto.getNomMov()));
            }

            updateQtaAndCtv(pos, counters);

            util.saveData(dto.getInstitute(), data);
        }
    }

    //11 to 14
    @Override
    public void orderCancellation(Cancellation dto) {
        //se sottoscrizione:
        //- CSS = - ctv-cos
        //- QSS = - nominale
        //se rimborso:
        //- CRS = - ctv-cos
        //- QRS = - nominale
        CommonDocument data = util.findOrCreateDocumentByNdg(dto.getInstitute(), dto.getNdg());
        if (data != null) {
            Position pos = findOrCreatePosition(data, dto.getProductId(), dto.getProductCode());
            InternalCounters counters = pos.getInternalCounters();

            switch (dto.getReason()) {
                case REASON_SOTT: {
                    log.info("PositionService.orderCancellation - Updating css and qss");
                    counters.setCss(counters.getCss().subtract(dto.getCtvCos()));
                    counters.setQss(counters.getQss().subtract(dto.getNomMov()));
                }
                case REASON_RIMB: {
                    log.info("PositionService.orderCancellation - Updating crs and qrs");
                    counters.setCrs(counters.getCrs().subtract(dto.getCtvCos()));
                    counters.setQrs(counters.getQrs().subtract(dto.getNomMov()));
                }
            }
            updateQtaAndCtv(pos, counters);

            util.saveData(dto.getInstitute(), data);
        }
    }

    //15
    @Override
    public void insertFundOrder(FundOrderCreation dto) {

        CommonDocument data = util.findOrCreateDocumentByNdg(dto.getInstitute(), dto.getNdg());
        if (data != null) {

            Position pos = findOrCreatePosition(data, dto.getProductId(), dto.getProductCode());

            //quantità interna = + nominale
            log.info("PositionService.insertFundOrder - Updating qtaInt");
            InternalCounters counters = pos.getInternalCounters();
            counters.setQtaint(counters.getQtaint().add(dto.getNomMov()));
            pos.getDetail().setQqta(BigDecimal.ZERO); //(p)
            pos.getDetail().setIvalbas(BigDecimal.ZERO); //(p)

            updateQtaAndCtv(pos, counters);

            util.saveData(dto.getInstitute(), data);
        }
    }

}
