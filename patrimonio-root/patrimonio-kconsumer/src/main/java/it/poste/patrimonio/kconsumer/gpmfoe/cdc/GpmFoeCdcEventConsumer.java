package it.poste.patrimonio.kconsumer.gpmfoe.cdc;

import it.poste.patrimonio.bl.service.IMasterDataService;
import it.poste.patrimonio.bl.service.IPACService;
import it.poste.patrimonio.bl.service.IPositionService;
import it.poste.patrimonio.event.business.impl.gpmfoe.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(
        groupId = "${patrimonio.kafka.group-id}",
        topics = "${patrimonio.kafka.topic.gpmgoe.cdc-events}",
        containerFactory = "GpmFoeCdcEventKLCFactory"
)
public class GpmFoeCdcEventConsumer {

    @KafkaHandler
    public void handle(String key, String message) {
        System.out.println("received message, key: " + key + " message: " + message );
    }

}
