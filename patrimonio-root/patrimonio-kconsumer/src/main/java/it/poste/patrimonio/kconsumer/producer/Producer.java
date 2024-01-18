package it.poste.patrimonio.kconsumer.producer;


import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import it.poste.patrimonio.kconsumer.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Producer<TKey, TEvent> {


  private final KafkaTemplate<TKey, TEvent> kafkaTemplate;
  
  private KafkaConfig conf;

  public Producer(KafkaTemplate<TKey, TEvent> kafkaTemplate, KafkaConfig conf) {
    this.kafkaTemplate = kafkaTemplate;
    this.conf = conf;
  }

  @Transactional("kafkaTransactionManager")
  public void sendMessage(TKey key, TEvent event){

	  String topicName=conf.getTopicName();

	  CompletableFuture<SendResult<TKey, TEvent>> future = kafkaTemplate.send(topicName, key, event);

	  //This will check producer result asynchronously to avoid thread blocking
	  future.whenComplete((result, ex) -> {

		  if (ex != null) {
			  log.error("Unable to send message on topic \"{}\", message=[{}] due to : {}", topicName, event , ex.getMessage(), ex);
		  } else if (result != null) {
			  log.info("Sent message=[{}] with offset=[{}] in topic \"{}\" partition=[{}]", event , result.getRecordMetadata().offset(), result.getRecordMetadata().topic(), result.getRecordMetadata().partition());
		  }
	  });


  }
}