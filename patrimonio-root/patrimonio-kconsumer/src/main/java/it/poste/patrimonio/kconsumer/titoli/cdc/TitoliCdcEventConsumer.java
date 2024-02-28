package it.poste.patrimonio.kconsumer.titoli.cdc;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(
        groupId = "${patrimonio.kafka.group-id}",
        topics = "${patrimonio.kafka.topic.titoli.cdc-events}",
        containerFactory = "TitoliCdcEventKLCFactory"
)
public class TitoliCdcEventConsumer {

    @KafkaHandler
    public void handle(String key, String message) {
        System.out.println("received message, key: " + key + " message: " + message );
    }

}
