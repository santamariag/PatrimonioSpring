package it.poste.patrimonio.bl.service.impl;

import it.poste.patrimonio.bl.service.IGpmFoeService;
import it.poste.patrimonio.bl.util.BusinessLogicUtil;
import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.db.model.Gpm;
import it.poste.patrimonio.db.repository.IFoeRepository;
import it.poste.patrimonio.db.repository.IGpmRepository;
import it.poste.patrimonio.event.business.impl.gpmfoe.AfbEvent;
import it.poste.patrimonio.event.business.impl.gpmfoe.MfmEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GpmFoeService implements IGpmFoeService {
    @Autowired
    IFoeRepository foeRepository;
    @Autowired
    IGpmRepository gpmRepository;
    @Autowired
    BusinessLogicUtil util;
    @Override
    public void updateFoe(AfbEvent message) {
        Foe foe=new Foe();
        foe.getPatrimonioOld().getPosizioni().forEach(p->{
            p.getInternalCountersGpmFoe().setCs(message.getCtv());
            p.getInternalCountersGpmFoe().setQs(message.getQta());
            p.getDetail().setQqta(util.calculateQqta(p));
            p.getDetail().setIvalbas(util.calculateCtv(p));
            p.getDetail().setDulprz(message.getReferenceDate());
            p.getDetail().setIprzat(message.getPrice());
        });
        foeRepository.save(foe);
    }

    @Override
    public void updateGpm(MfmEvent message) {
        Gpm gpm=new Gpm();
        gpm.getPatrimonioOld().getPosizioni().forEach(p->{
            p.getInternalCountersGpmFoe().setCs(message.getCtv());
            p.getInternalCountersGpmFoe().setQs(message.getQtaSub().subtract(message.getQtaRef()));
            p.getDetail().setQqta(util.calculateQqta(p));
            p.getDetail().setIvalbas(util.calculateCtv(p));
            // p.getDetail().setDulprz(message.getReferenceDate());
        });
        gpmRepository.save(gpm);
    }
}
