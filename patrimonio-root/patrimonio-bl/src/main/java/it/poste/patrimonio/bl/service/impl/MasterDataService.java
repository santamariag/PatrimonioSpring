package it.poste.patrimonio.bl.service.impl;
import it.poste.patrimonio.db.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.poste.patrimonio.bl.service.IMasterDataService;
import it.poste.patrimonio.bl.util.PositionUtil;
import it.poste.patrimonio.db.model.CommonData;
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
		data.setStatus(Status.ACTIVE);
		//data.setCustomerDescr();
		//data.setVersion();
		//data.setExternalKeys();
		util.saveData(dto.getInstitute(), data);
	}
	public void masterDataDelete(MasterDataDelete dto){
		CommonData data = util.findDataByNdg(dto.getInstitute(), dto.getNdg());
		util.deleteData(dto.getInstitute(), data);
	}

	public void masterDataLock(MasterDataLock dto){
		CommonData data = util.findDataByNdg(dto.getInstitute(), dto.getNdg());
		data.setStatus(Status.BLOCKED);
		util.saveData(dto.getInstitute(), data);
	}
}
