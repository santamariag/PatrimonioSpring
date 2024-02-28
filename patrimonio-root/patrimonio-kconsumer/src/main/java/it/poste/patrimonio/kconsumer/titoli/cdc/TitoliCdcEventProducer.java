package it.poste.patrimonio.kconsumer.titoli.cdc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitoliCdcEventProducer {

    @Value("${patrimonio.kafka.topic.titoli.cdc-events}")
    String topic;

    @Autowired()
    @Qualifier("TitoliCdcEventKTFactory")
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendEvent(String key, String message) {
        kafkaTemplate.send(topic, key, message);
    }

}
