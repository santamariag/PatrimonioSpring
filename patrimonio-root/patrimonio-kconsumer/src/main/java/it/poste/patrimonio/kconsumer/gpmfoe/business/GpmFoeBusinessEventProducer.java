package it.poste.patrimonio.kconsumer.gpmfoe.business;

import it.poste.patrimonio.event.business.model.IGpmFoeBusinessEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class GpmFoeBusinessEventProducer  {

    @Value("${patrimonio.kafka.topic.gpmgoe.business-events}")
    String topic;

    @Autowired()
    @Qualifier("GpmFoeBusinessEventKTFactory")
    private KafkaTemplate<String, IGpmFoeBusinessEvent> kafkaTemplate;

    public void sendEvent(IGpmFoeBusinessEvent message) {
        kafkaTemplate.send(topic, message.getKey(), message);
    }

}
