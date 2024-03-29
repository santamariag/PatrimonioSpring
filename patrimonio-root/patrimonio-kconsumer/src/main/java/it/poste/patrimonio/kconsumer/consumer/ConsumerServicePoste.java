package it.poste.patrimonio.kconsumer.consumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import it.poste.patrimonio.itf.model.KeyDTO;
import it.poste.patrimonio.itf.model.ValueDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Profile("poste")
public class ConsumerServicePoste {


	@KafkaListener(topics = "${kafka.topicName}", containerFactory = "kafkaListenerContainerFactoryTest", concurrency = "${kafka.consumer.concurrency:1}",  properties = {"auto.offset.reset = earliest"})
	public void consumeTest(ConsumerRecord<KeyDTO, ValueDTO> cr) {

		log.info("----- Received message: {}", cr.toString());

		log.info("Key {}", cr.key());

		log.info("Value {} ",cr.value());

	}
}