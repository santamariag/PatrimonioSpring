package it.poste.patrimonio.bl.service.impl;

import it.poste.patrimonio.bl.service.IPositionService;
import it.poste.patrimonio.event.business.impl.gpmfoe.*;
import org.springframework.stereotype.Service;

@Service
public class PositionService implements IPositionService {
    @Override
    public void insertSubOrder(SubscriptionOrder order) {

    }

    @Override
    public void subOrderFeedback(SubscriptionFeedback feedback) {

    }

    @Override
    public void insertRefundOrder(RefundOrder order) {

    }

    @Override
    public void refundOrderFeedback(RefundFeedback feedback) {

    }

    @Override
    public void orderCancellationFromBackOffice(CancellationFromBackOffice canc) {

    }

    @Override
    public void orderCancellationFromClient(CancellationFromClient canc) {

    }

    @Override
    public void orderCancellationForChareFailure(CancellationForChargeFailure canc) {

    }

    @Override
    public void orderCancellationFromProductCompany(CancellationFromProductCompany canc) {

    }

    @Override
    public void insertFundOrder(FundOrderCreation order) {

    }
}
