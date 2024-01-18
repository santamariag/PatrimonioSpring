package it.poste.patrimonio.bl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.poste.patrimonio.bl.service.IBPService;
import it.poste.patrimonio.bl.service.ICCService;
import it.poste.patrimonio.bl.service.ICardService;
import it.poste.patrimonio.bl.service.IFoeService;
import it.poste.patrimonio.bl.service.IFondiService;
import it.poste.patrimonio.bl.service.IGpmService;
import it.poste.patrimonio.bl.service.ILibrettiService;
import it.poste.patrimonio.bl.service.IOrchestratorService;
import it.poste.patrimonio.bl.service.IPolizzePrService;
import it.poste.patrimonio.bl.service.IPolizzeService;
import it.poste.patrimonio.bl.service.IRNdgService;
import it.poste.patrimonio.bl.service.ITitoliService;
import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.db.model.Gpm;
import it.poste.patrimonio.db.model.Titoli;
import it.poste.patrimonio.rs.specs.model.DettaglioPatrimonioInput;
import it.poste.patrimonio.rs.specs.model.PatrimonioClienteOutputElementNs1;

@Service
public class OrchestratorService implements IOrchestratorService {
	
	@Autowired
	private IGpmService gpmService;
	
	@Autowired
	private IFoeService foeService;
	
	@Autowired
	private ITitoliService titoliService;
	
	@Autowired
	private IBPService bpService;
	
	@Autowired
	private ICardService cardService;
	
	@Autowired
	private ICCService ccService;
	
	@Autowired
	private IFondiService fondiService;
	
	@Autowired
	private ILibrettiService librettiService;
	
	@Autowired
	private IRNdgService rNdgService;
	
	@Autowired
	private IPolizzeService polizzeService;
	
	@Autowired
	private IPolizzePrService polizzePrService;

	@Override
	public PatrimonioClienteOutputElementNs1 retrievePatrimonioCliente(
			DettaglioPatrimonioInput dettaglioPatrimonioInput) {
		
		PatrimonioClienteOutputElementNs1 output = null;
		if (stringToBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagGPM())) {
			output = gpmService.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());

		}
		if (stringToBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagGestionePrivate())) { 
			PatrimonioClienteOutputElementNs1 outputtmp = foeService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		if (stringToBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagTitoli())) {
			PatrimonioClienteOutputElementNs1 outputtmp = titoliService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		
		if (stringToBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagBuoniPostali())) {
			PatrimonioClienteOutputElementNs1 outputtmp = bpService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		
		if (stringToBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagCc())) {
			PatrimonioClienteOutputElementNs1 outputtmp = ccService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		
		if (stringToBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagFondi())) {
			PatrimonioClienteOutputElementNs1 outputtmp = fondiService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		
		if (stringToBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagLibretti())) {
			PatrimonioClienteOutputElementNs1 outputtmp = librettiService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		
		if (stringToBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagNdgLegati())) {
			PatrimonioClienteOutputElementNs1 outputtmp = rNdgService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		
		if (stringToBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagPolizze())) {
			PatrimonioClienteOutputElementNs1 outputtmp = polizzeService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		
		if (stringToBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagPolizzeProtezione())) {
			PatrimonioClienteOutputElementNs1 outputtmp = polizzePrService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		
		if (stringToBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagCarte())) {
			PatrimonioClienteOutputElementNs1 outputtmp = cardService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioCards().addAll(outputtmp.getDettaglioCards());

				} else
					output = outputtmp;
			}
		}
		return output;
	}
	
	
	@Override
	public void add(Gpm gpm) {
		
		gpmService.add(gpm);		
	}

	@Override
	public void add(Foe foe) {
		
		foeService.add(foe);		
	}

	@Override
	public void add(Titoli titoli) {
		
		titoliService.add(titoli);		
	}
	
	private boolean stringToBoolean(String flag) {
		if(flag!=null && !flag.isBlank()&& 
				(flag.equals("S") || flag.equals("Y")) ) {
			return true;	
		}
		return false;
	}


}
