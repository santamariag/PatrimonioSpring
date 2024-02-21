package it.poste.patrimonio.kconsumer.titoli.business;


import it.poste.patrimonio.event.business.model.ITitoliBusinessEvent;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(
        groupId = "${patrimonio.kafka.group-id}",
        topics = "${patrimonio.kafka.topic.titoli.business-events}",
        containerFactory = "TitoliBusinessEventKLCFactory"
)
public class TitoliBusinessEventConsumer {

    @KafkaHandler
    public void handle(ITitoliBusinessEvent message) {
        System.out.println("Received ITitoliBusinessEvent, key: " + message.getKey() );
    }

}
