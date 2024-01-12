package it.poste.patrimonio.rs.api.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.poste.patrimonio.bl.exception.service.IFoeService;
import it.poste.patrimonio.bl.exception.service.IGpmService;
import it.poste.patrimonio.bl.exception.service.ITitoliService;
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

	@Override
	public ResponseEntity<PatrimonioClienteOutputElementNs1> dettaglioPatrimonioPost(
			@Valid DettaglioPatrimonioInput dettaglioPatrimonioInput, Optional<String> source,
			Optional<String> requestID, Optional<String> routingRole, Optional<String> creationTime) {
		
		log.info("SOURCE "+source);
		log.info("REQUEST ID "+requestID);
		log.info("ROUTING ROLE "+routingRole);
		log.info("CREATION TIME "+creationTime);
		
		log.info("REQUEST "+dettaglioPatrimonioInput);
		
		
		/*PatrimonioClienteOutputElementNs1 output=new PatrimonioClienteOutputElementNs1();
		
		EsitoTypeTypeNs2 esito=new EsitoTypeTypeNs2();
		esito.setEsito("OK");
		
		output.setEsito(esito);
		
		DettaglioPatrimonioTypeTypeNs2 dettaglioP=new DettaglioPatrimonioTypeTypeNs2();
		dettaglioP.setNdg("12345");
		
		List<DettaglioPatrimonioTypeTypeNs2> dettaglioPList=new ArrayList<>();
		dettaglioPList.add(dettaglioP);
		
		output.setDettaglioPatrimonio(dettaglioPList);
		
		
		Ww0aOTabpatTabCpTypeNs2 dettaglioC=new Ww0aOTabpatTabCpTypeNs2();
		dettaglioC.setIdFamigliaProdotto("1");
		dettaglioC.setNdg("123456");
		
		List<Ww0aOTabpatTabCpTypeNs2> dettaglioCList=new ArrayList<>();
		dettaglioCList.add(dettaglioC);
		
		output.setDettaglioCards(dettaglioCList);
		
		if(source.isPresent()) {
			if(source.get().equals("Postman"))
				throw new PatrimonioNotFoundException("Not Found", dettaglioPatrimonioInput.getPatrimonioClienteInput().getCfNdg());
		}*/
		PatrimonioClienteOutputElementNs1 output = null;
		if (stringtoBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagGPM())) {
			output = gpmService.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());

		}
		if (stringtoBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagTitoli())) {
			PatrimonioClienteOutputElementNs1 outputtmp = titoliService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if (output != null) {
				output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

			} else
				output = outputtmp;
		}
		/**
		 * Da capire se il flag fondi governa solo il verticale fondi o anche quello foe
		 * -se governa entrambi i verticali allora la sezione deve essere costruita sia andando sul nostro db che invocando servizio per recuperare i dati di fondi
		 * -se governa solo il verticale fondi allora probabilmente anche foe è indirizzato dal flag gpm che quindi porterà all'interrogazione di entrambe le collection gpm e foe
		 */
		if (stringtoBoolean(dettaglioPatrimonioInput.getPatrimonioClienteInput().getFlagFondi())) { 
			PatrimonioClienteOutputElementNs1 outputtmp = foeService
					.findByNdgs(dettaglioPatrimonioInput.getPatrimonioClienteInput().getNdgList().getNdg());
			if (output != null) {
				output.getDettaglioPatrimonio().addAll(outputtmp.getDettaglioPatrimonio());

			} else
				output = outputtmp;
		}
		if (output == null) {
			output = new PatrimonioClienteOutputElementNs1();
			output.setEsito(new EsitoTypeTypeNs2()
					.esito("KO")
					.addDettaglioErroreItem(new DettaglioErroreTypeTypeNs2()
							.codiceErrore("TODO")
							.descrizioneErrore("TODO"))); //TODO

		}
		output.setEsito(new EsitoTypeTypeNs2().esito("OK"));
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
	
	public boolean stringtoBoolean(String flag) {
		if(flag!=null && !flag.isBlank()&& 
				(flag.equals("true") || flag.equals("S") || flag.equals("Y")) ) {
		return true;	
	}
		return false;
	}
}
