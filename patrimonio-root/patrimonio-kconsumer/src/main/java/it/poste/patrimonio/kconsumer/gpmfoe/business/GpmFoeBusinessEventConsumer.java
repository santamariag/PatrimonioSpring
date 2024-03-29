package it.poste.patrimonio.kconsumer.gpmfoe.business;
import it.poste.patrimonio.bl.service.*;
import it.poste.patrimonio.event.business.impl.gpmfoe.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(
        groupId = "${patrimonio.kafka.group-id}",
        topics = "${patrimonio.kafka.topic.gpmgoe.business-events}",
        containerFactory = "GpmFoeBusinessEventKLCFactory"
)
public class GpmFoeBusinessEventConsumer {
    @Autowired
    IGpmFoeService gpmFoeService;

    @Autowired
    IMasterDataService masterDataService;

    @Autowired
    IPACService pacService;

    @Autowired
    IPositionService positionService;

    @KafkaHandler
    public void handleCancellationForChargeFailureEvent(Cancellation message) {
        //TODO aggiungere in questi metodi provenienza ordine di cancellazione?
        positionService.orderCancellation(message);
    }

    @KafkaHandler
    public void handleCancellationFromBackOfficeEvent(Cancellation message) {
        positionService.orderCancellation(message);
    }

    @KafkaHandler
    public void handleCancellationFromClientEvent(Cancellation message) {
        positionService.orderCancellation(message);
    }

    @KafkaHandler
    public void handleCancellationFromProductCompanyEvent(Cancellation message) {
        positionService.orderCancellation(message);
    }

    @KafkaHandler
    public void handleFundOrderCreationEvent(FundOrderCreation message) {
        positionService.insertFundOrder(message);
    }

    @KafkaHandler
    public void handleMasterDataCreationEvent(MasterDataCreation message) {
        masterDataService.masterDataCreation(message);
    }

    @KafkaHandler
    public void handleMasterDataDeleteEvent(MasterDataDelete message) {
        masterDataService.masterDataDelete(message);
    }

    @KafkaHandler
    public void handleMasterDataLockEvent(MasterDataLock message) {
        masterDataService.masterDataLock(message);
    }

    @KafkaHandler
    public void handlePacCreationEvent(PacCreation message) {
        pacService.pacCreation(message);
    }

    @KafkaHandler
    public void handlePacDeleteEvent(PacDelete message) {
        pacService.pacDelete(message);
    }

    @KafkaHandler
    public void handleRefundFeedbackEvent(RefundFeedback message) {
        positionService.refundOrderFeedback(message);
    }

    @KafkaHandler
    public void handleRefundOrderEvent(RefundOrder message) {
        positionService.insertRefundOrder(message);
    }

    @KafkaHandler
    public void handleSubscriptionFeedbackEvent(SubscriptionFeedback message) {
        positionService.subOrderFeedback(message);
    }

    @KafkaHandler
    public void handleSubscriptionOrderEvent(SubscriptionOrder message) {
        positionService.insertSubOrder(message);
    }

    @KafkaHandler
    public void handleAFBItemEvent(AfbEvent message) {
        gpmFoeService.updateFoe(message);
    }
    @KafkaHandler
    public void handleMFMItemEvent(MfmEvent message) {
        gpmFoeService.updateGpm(message);
    }

}
