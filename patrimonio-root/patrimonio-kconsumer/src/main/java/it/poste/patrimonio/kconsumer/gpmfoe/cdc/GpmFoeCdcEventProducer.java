package it.poste.patrimonio.kconsumer.gpmfoe.cdc;

import it.poste.patrimonio.event.business.model.IGpmFoeBusinessEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class GpmFoeCdcEventProducer {

    @Value("${patrimonio.kafka.topic.gpmgoe.cdc-events}")
    String topic;

    @Autowired()
    @Qualifier("GpmFoeCdcEventKTFactory")
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendEvent(String key, String message) {
        kafkaTemplate.send(topic, key, message);
    }

}
