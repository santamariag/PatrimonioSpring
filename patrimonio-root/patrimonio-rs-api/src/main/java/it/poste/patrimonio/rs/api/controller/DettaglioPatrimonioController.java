package it.poste.patrimonio.rs.api.controller;

import java.util.ArrayList;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.poste.patrimonio.bl.service.IOrchestratorService;
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
	private IOrchestratorService service;

	@Override
	public ResponseEntity<PatrimonioClienteOutputElementNs1> dettaglioPatrimonioPost(
			DettaglioPatrimonioInput dettaglioPatrimonioInput, Optional<String> source,
			Optional<String> requestID, Optional<String> routingRole, Optional<String> creationTime) {
		
		log.info("SOURCE "+source);
		log.info("REQUEST ID "+requestID);
		log.info("ROUTING ROLE "+routingRole);
		log.info("CREATION TIME "+creationTime);
		
		log.info("REQUEST "+dettaglioPatrimonioInput);
		
		PatrimonioClienteOutputElementNs1 output = service.retrievePatrimonioCliente(dettaglioPatrimonioInput);
		
		
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
		
		service.add(gpm);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
		
	}
	
	@PostMapping(value = "/addFoe")
	public ResponseEntity<Void> addDettaglioPatrimonioFoe(@RequestBody Foe foe) {
		
		log.info("REQUEST "+foe);
		
		service.add(foe);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
		
	}
	
	@PostMapping(value = "/addTitoli")
	public ResponseEntity<Void> addDettaglioPatrimonioTitoli(@RequestBody Titoli titoli) {
		
		log.info("REQUEST "+titoli);
		
		service.add(titoli);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
		
	}
	
}
