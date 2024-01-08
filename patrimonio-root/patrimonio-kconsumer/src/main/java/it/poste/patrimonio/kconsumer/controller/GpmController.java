package it.poste.patrimonio.kconsumer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.poste.patrimonio.itf.model.GpmDTO;
import it.poste.patrimonio.kconsumer.producer.Producer;
import it.poste.patrimonio.kconsumer.util.KeyExtractor;

@RestController
@RequestMapping("/api")
public class GpmController {

	private final Producer<String, GpmDTO> producer;

	public GpmController(Producer<String, GpmDTO> producer) {
		this.producer = producer;
	}


	@PostMapping(value = "/gpm")
	public void insertGpm(@RequestBody GpmDTO gpm) {

		producer.sendMessage(new KeyExtractor<String, GpmDTO>() {

			@Override
			public String extractKey(GpmDTO event) 
			{
				return String.valueOf(event.getNdg());
			}
		}.extractKey(gpm),  gpm);

	}
}