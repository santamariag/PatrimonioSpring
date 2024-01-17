package it.poste.patrimonio.rs.api.controller;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.poste.patrimonio.bl.service.IBPService;
import it.poste.patrimonio.bl.service.ICCService;
import it.poste.patrimonio.bl.service.ICardService;
import it.poste.patrimonio.bl.service.IFoeService;
import it.poste.patrimonio.bl.service.IFondiService;
import it.poste.patrimonio.bl.service.IGpmService;
import it.poste.patrimonio.bl.service.ILibrettiService;
import it.poste.patrimonio.bl.service.IPolizzePrService;
import it.poste.patrimonio.bl.service.IPolizzeService;
import it.poste.patrimonio.bl.service.ITitoliService;
import it.poste.patrimonio.bl.service.IRNdgService;
import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.db.model.Gpm;
import it.poste.patrimonio.db.model.Titoli;
import it.poste.patrimonio.rs.specs.api.DefaultApi;
import it.poste.patrimonio.rs.specs.model.DettaglioErroreTypeTypeNs2;
import it.poste.patrimonio.rs.specs.model.DettaglioPatrimonioInput;
import it.poste.patrimonio.rs.specs.model.EsitoTypeTypeNs2;
import it.poste.patrimonio.rs.specs.model.PatrimonioClienteOutputElementNs1;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class DettaglioPatrimonioController implements DefaultApi{
	
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
	public ResponseEntity<PatrimonioClienteOutputElementNs1> dettaglioPatrimonioPost(
			@Valid DettaglioPatrimonioInput dettaglioPatrimonioInput, Optional<String> source,
			Optional<String> requestID, Optional<String> routingRole, Optional<String> creationTime) {
		
		log.info("SOURCE "+source);
		log.info("REQUEST ID "+requestID);
		log.info("ROUTING ROLE "+routingRole);
		log.info("CREATION TIME "+creationTime);
		
		log.info("REQUEST "+dettaglioPatrimonioInput);
		
		PatrimonioClienteOutputElementNs1 output = null;
		if (stringtoBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagGPM())) {
			output = gpmService.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());

		}
		if (stringtoBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagGestionePrivate())) { 
			PatrimonioClienteOutputElementNs1 outputtmp = foeService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		if (stringtoBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagTitoli())) {
			PatrimonioClienteOutputElementNs1 outputtmp = titoliService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		
		if (stringtoBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagBuoniPostali())) {
			PatrimonioClienteOutputElementNs1 outputtmp = bpService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		
		if (stringtoBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagCc())) {
			PatrimonioClienteOutputElementNs1 outputtmp = ccService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		
		if (stringtoBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagFondi())) {
			PatrimonioClienteOutputElementNs1 outputtmp = fondiService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		
		if (stringtoBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagLibretti())) {
			PatrimonioClienteOutputElementNs1 outputtmp = librettiService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		
		if (stringtoBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagNdgLegati())) {
			PatrimonioClienteOutputElementNs1 outputtmp = rNdgService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		
		if (stringtoBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagPolizze())) {
			PatrimonioClienteOutputElementNs1 outputtmp = polizzeService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		
		if (stringtoBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagPolizzeProtezione())) {
			PatrimonioClienteOutputElementNs1 outputtmp = polizzePrService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

				} else
					output = outputtmp;
			}
		}
		
		if (stringtoBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagCarte())) {
			PatrimonioClienteOutputElementNs1 outputtmp = cardService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if(outputtmp!=null) {
				if (output != null) {
					output.getDettaglioCards().addAll(outputtmp.getDettaglioCards());

				} else
					output = outputtmp;
			}
		}
		
		
		if (output == null) {
			output = new PatrimonioClienteOutputElementNs1();
			output.setEsito(new EsitoTypeTypeNs2()
					.esito("KO")
					.addDettaglioErroreItem(new DettaglioErroreTypeTypeNs2()
							.codiceErrore("TODO")
							.descrizioneErrore("TODO"))); //TODO

		} else
			output.setEsito(new EsitoTypeTypeNs2().esito("OK").dettaglioErrore(new ArrayList<>()));
		
		return ResponseEntity.ok(output);
	}

	@PostMapping(value = "/addGpm", consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> addDettaglioPatrimonioGpm(@RequestBody Gpm gpm) {
		
		log.info("REQUEST "+gpm);
		
		gpmService.add(gpm);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
		
	}
	
	@PostMapping(value = "/addFoe")
	public ResponseEntity<Void> addDettaglioPatrimonioFoe(@RequestBody Foe foe) {
		
		log.info("REQUEST "+foe);
		
		foeService.add(foe);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
		
	}
	
	@PostMapping(value = "/addTitoli")
	public ResponseEntity<Void> addDettaglioPatrimonioTitoli(@RequestBody Titoli titoli) {
		
		log.info("REQUEST "+titoli);
		
		titoliService.add(titoli);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
		
	}
	
	private boolean stringtoBoolean(String flag) {
		if(flag!=null && !flag.isBlank()&& 
				(flag.equals("S") || flag.equals("Y")) ) {
		return true;	
	}
		return false;
	}
}
