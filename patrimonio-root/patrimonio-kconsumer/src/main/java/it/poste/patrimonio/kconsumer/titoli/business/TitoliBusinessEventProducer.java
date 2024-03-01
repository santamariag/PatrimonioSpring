package it.poste.patrimonio.kconsumer.titoli.business;


import it.poste.patrimonio.event.business.model.ITitoliBusinessEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitoliBusinessEventProducer {

    @Value("${patrimonio.kafka.topic.titoli.business-events}")
    String topic;

    @Autowired()
    @Qualifier("TitoliBusinessEventKTFactory")
    private KafkaTemplate<String, ITitoliBusinessEvent> kafkaTemplate;

    public void sendEvent(ITitoliBusinessEvent message) {
        kafkaTemplate.send(topic, message.getKafkaKey(), message);
    }

}
