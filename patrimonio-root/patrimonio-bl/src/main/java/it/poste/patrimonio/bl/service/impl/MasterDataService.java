package it.poste.patrimonio.bl.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.poste.patrimonio.bl.service.IMasterDataService;
import it.poste.patrimonio.bl.util.PositionUtil;
import it.poste.patrimonio.db.model.CommonData;
import it.poste.patrimonio.db.model.Position;
import it.poste.patrimonio.event.business.model.MasterDataCreation;
import it.poste.patrimonio.event.business.model.MasterDataDelete;
import it.poste.patrimonio.event.business.model.MasterDataLock;

@Service
public class MasterDataService implements IMasterDataService {

	@Autowired
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

		/**
		 * Direi che questo non va bene e non serve
		 * quando si crea il cliente non si crea alcuna posizione
		 * Quando arrivarà l 'evento che crea una posizione si creerà la posizione aggiungendola 
		 * alla lista e i dati necessari che stanno anche nel contenitore vengono copiati nella posizione
		 */
		data.getPatrimonioOld().getPosizioni().forEach(p->{
				p.getDetail().setCage(dto.getAgency());
				p.getDetail().setNdg(dto.getNdg());
			p.getDetail().setCsdp(dto.getIndex()); //csdp==rubrica?
			p.getDetail().setXint(dto.getClientDesc());
			/**
			 * Da excel non mi risulta questosto campo per Gpm/Foe quindi non va popolato
			 */
			//p.getDetail().setXstt(dto.getStatus()); //stato attivo/bloccato?
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
