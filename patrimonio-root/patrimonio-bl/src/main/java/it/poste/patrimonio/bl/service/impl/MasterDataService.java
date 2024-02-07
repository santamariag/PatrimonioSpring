package it.poste.patrimonio.bl.service.impl;
import it.poste.patrimonio.bl.service.IFoeService;
import it.poste.patrimonio.bl.service.IGpmService;
import it.poste.patrimonio.bl.service.IMasterDataService;
import it.poste.patrimonio.bl.util.PositionUtil;
import it.poste.patrimonio.db.model.*;
import it.poste.patrimonio.event.business.model.MasterDataCreation;
import it.poste.patrimonio.event.business.model.MasterDataDelete;
import it.poste.patrimonio.event.business.model.MasterDataLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MasterDataService implements IMasterDataService {

	PositionUtil util;

	public void masterDataCreation(MasterDataCreation dto){
		CommonData data = new CommonData();
		data.setNdg(dto.getNdg());
		data.getDeposit().setAgency(dto.getAgency());
		data.getDeposit().setBranch(dto.getBranch());
		data.getDeposit().setIndex(dto.getIndex());
		data.setFiscalCode(dto.getTaxIdCode());
		data.setInternalCustomerCode(dto.getClientIntCode());
		data.setRapporto(dto.getDoss());
		//data.setCustomerDescr();
		//data.setVersion();
		//data.setExternalKeys();

		data.getPatrimonioOld().getPosizioni().forEach(p->{
				p.getDetail().setCage(dto.getAgency());
				p.getDetail().setNdg(dto.getNdg());
			p.getDetail().setCsdp(dto.getIndex()); //csdp==rubrica?
			p.getDetail().setXint(dto.getClientDesc());
			p.getDetail().setXstt(dto.getStatus()); //stato attivo/bloccato?
			p.getDetail().setTrap(dto.getInstitute());
			});

		util.saveData(dto.getInstitute(), data);
	}
	public void masterDataDelete(MasterDataDelete dto){
		CommonData data = util.findDataByNdg(dto.getInstitute(), dto.getNdg());
		util.deleteData(dto.getInstitute(), data);
	}

	public void masterDataLock(MasterDataLock dto){
		CommonData data = util.findDataByNdg(dto.getInstitute(), dto.getNdg());
		for (Position pos : data.getPatrimonioOld().getPosizioni()){
			pos.getDetail().setXstt("Blocked");
		}
		util.saveData(dto.getInstitute(), data);
	}
}
