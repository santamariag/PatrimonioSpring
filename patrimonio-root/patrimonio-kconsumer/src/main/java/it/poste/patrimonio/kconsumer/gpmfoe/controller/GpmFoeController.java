package it.poste.patrimonio.kconsumer.gpmfoe.controller;

import it.poste.patrimonio.event.business.impl.gpmfoe.*;

import it.poste.patrimonio.kconsumer.gpmfoe.business.GpmFoeBusinessEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gpmfoe")
public class GpmFoeController {

    @Autowired
    private GpmFoeBusinessEventProducer gpmFoeBusinessEventProducer;

    @PostMapping("/send")
    public String sendMessage(@RequestParam("message") String message) {

        CancellationForChargeFailure cancellationForChargeFailure = new CancellationForChargeFailure();
        CancellationFromBackOffice cancellationFromBackOffice = new CancellationFromBackOffice();
        CancellationFromClient cancellationFromClient = new CancellationFromClient();
        CancellationFromProductCompany cancellationFromProductCompany = new CancellationFromProductCompany();
        FundOrderCreation fundOrderCreation = new FundOrderCreation();
        MasterDataCreation masterDataCreation = new MasterDataCreation();
        MasterDataDelete masterDataDelete = new MasterDataDelete();
        MasterDataLock masterDataLock = new MasterDataLock();
        PacCreation pacCreation = new PacCreation();
        PacDelete pacDelete = new PacDelete();
        RefundFeedback refundFeedback = new RefundFeedback();
        RefundOrder refundOrder = new RefundOrder();
        SubscriptionFeedback subscriptionFeedback = new SubscriptionFeedback();
        SubscriptionOrder subscriptionOrder = new SubscriptionOrder();

        cancellationForChargeFailure.setInstitute(message);
        cancellationFromBackOffice.setInstitute(message);
        cancellationFromClient.setInstitute(message);
        cancellationFromProductCompany.setInstitute(message);
        fundOrderCreation.setInstitute(message);
        masterDataCreation.setInstitute(message);
        masterDataDelete.setInstitute(message);
        masterDataLock.setInstitute(message);
        pacCreation.setInstitute(message);
        pacDelete.setInstitute(message);
        refundFeedback.setInstitute(message);
        refundOrder.setInstitute(message);
        subscriptionFeedback.setInstitute(message);
        subscriptionOrder.setInstitute(message);

        gpmFoeBusinessEventProducer.sendEvent(cancellationForChargeFailure);
        gpmFoeBusinessEventProducer.sendEvent(cancellationFromBackOffice);
        gpmFoeBusinessEventProducer.sendEvent(cancellationFromClient);
        gpmFoeBusinessEventProducer.sendEvent(cancellationFromProductCompany);
        gpmFoeBusinessEventProducer.sendEvent(fundOrderCreation);
        gpmFoeBusinessEventProducer.sendEvent(masterDataCreation);
        gpmFoeBusinessEventProducer.sendEvent(masterDataDelete);
        gpmFoeBusinessEventProducer.sendEvent(masterDataLock);
        gpmFoeBusinessEventProducer.sendEvent(pacCreation);
        gpmFoeBusinessEventProducer.sendEvent(pacDelete);
        gpmFoeBusinessEventProducer.sendEvent(refundFeedback);
        gpmFoeBusinessEventProducer.sendEvent(refundOrder);
        gpmFoeBusinessEventProducer.sendEvent(subscriptionFeedback);
        gpmFoeBusinessEventProducer.sendEvent(subscriptionOrder);

        return "Message sent: " + message;

    }

}