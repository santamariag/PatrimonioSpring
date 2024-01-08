package it.poste.patrimonio.kconsumer.consumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.poste.patrimonio.bl.exception.service.IGpmService;
import it.poste.patrimonio.itf.model.GpmDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsumerService {

	private final IGpmService gpmService;

	public ConsumerService(IGpmService gpmService) {
		this.gpmService= gpmService;
	}

	@KafkaListener(topics = "${kafka.topicName}", containerFactory = "kafkaListenerContainerFactory")
	@Transactional("transactionManager")
	public void consume(ConsumerRecord<String, Object> cr) {

		log.info("----- Received message: {}", cr.toString());

		if (cr.value() instanceof GpmDTO) {
			GpmDTO gpm = GpmDTO.class.cast(cr.value());
			gpmService.add(gpm);
			log.info("Inserted in Mongo "+ gpm);
			//throw new RuntimeException("Eccezione");
			
		}
		else {

			log.error("----- Message UNKNOWN!!!: {}", cr.toString());
		}

	}
}