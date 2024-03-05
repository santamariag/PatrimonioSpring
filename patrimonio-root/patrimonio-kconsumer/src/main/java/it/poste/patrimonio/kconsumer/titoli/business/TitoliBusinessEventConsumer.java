package it.poste.patrimonio.kconsumer.titoli.business;


import it.poste.patrimonio.bl.service.IPositionTitoliService;
import it.poste.patrimonio.event.business.impl.titoli.*;
import it.poste.patrimonio.event.business.model.Consts;
import it.poste.patrimonio.event.business.model.ITitoliBusinessEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@KafkaListener(
        groupId = "${patrimonio.kafka.group-id}",
        topics = "${patrimonio.kafka.topic.titoli.business-events}",
        containerFactory = "TitoliBusinessEventKLCFactory"
)
public class TitoliBusinessEventConsumer {

    @Autowired
    TitoliBusinessEventProducer titoliBusinessEventProducer;

    @Autowired
    private IPositionTitoliService service;

    @KafkaHandler
    public void handle(DepositEvent message) {
        System.out.println("Received Deposit Event , key: " + message.getKafkaKey() );
        service.processDepositEvent(message);
    }

    @KafkaHandler
    public void handle(PositionEvent message) {
        System.out.println("Received PositionEvent , key: " + message.getKafkaKey() );
        service.processPositionEvent(message);
    }

    @KafkaHandler
    public void handle(CtirEvent message) {
        System.out.println("Received CtirEvent, target: "+ message.getTarget() + ", key: " + message.getKafkaKey() );
        if (Consts.EV_TARGET_POSITION.equals(message.getTarget())){
            service.processCtirEventPosition(message);
        } else {
           service.processCtirEventFinancialInstrument(message, (event)-> titoliBusinessEventProducer.sendEvent(event) );
        }
    }

    @KafkaHandler
    public void handle(TitrEvent message) {
        System.out.println("Received TitrEvent, target: "+ message.getTarget() + ", key: " + message.getKafkaKey() );
        if (Consts.EV_TARGET_POSITION.equals(message.getTarget())){
            service.processTitrEventPosition(message);
        } else {
            service.processTitrEventFinancialInstrument(message, (event)-> titoliBusinessEventProducer.sendEvent(event));
        }
    }

    @KafkaHandler
    public void handle(PriceEvent message) {
        System.out.println("Received PriceEvent, target: "+ message.getTarget() + ", key: " + message.getKafkaKey() );
        if (Consts.EV_TARGET_POSITION.equals(message.getTarget())){
            service.processPriceEventPosition(message);
        } else {
           service.processPriceEventFinancialInstrument(message, (event)-> titoliBusinessEventProducer.sendEvent(event));

        }
    }

    @KafkaHandler
    public void handle(RprzEvent message) {
        System.out.println("Received RprzEvent, target: "+ message.getTarget() + ", key: " + message.getKafkaKey() );
        if (Consts.EV_TARGET_POSITION.equals(message.getTarget())){
            service.processRprzEventPosition(message);
        } else {
            service.processRprzEventFinancialInstrument(message, (event)-> titoliBusinessEventProducer.sendEvent(event));

        }
    }

    @KafkaHandler
    public void handle(ExchangeRateEvent message) {
        System.out.println("Received ExchangeRateEvent, target: "+ message.getTarget() + ", key: " + message.getKafkaKey() );
        if (Consts.EV_TARGET_POSITION.equals(message.getTarget())){
            service.processExchangeRateEventPosition(message);
        } else {
            service.processExchangeRateEvent(message, (event)-> titoliBusinessEventProducer.sendEvent(event));

        }
    }
}
