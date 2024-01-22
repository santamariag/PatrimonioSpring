package it.poste.patrimonio.kconsumer.consumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Profile("poste")
public class ConsumerServicePoste {

    
	@KafkaListener(topics = "${kafka.topicName}", containerFactory = "kafkaListenerContainerFactoryTest", concurrency = "${kafka.consumer.concurrency}",  properties = {"auto.offset.reset = earliest"})
	@Transactional("transactionManager")
	public void consumeTest(ConsumerRecord<String, String> cr) {

		log.info("----- Received message: {}", cr.toString());

	}
}