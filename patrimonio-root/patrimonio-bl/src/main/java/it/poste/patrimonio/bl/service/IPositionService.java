package it.poste.patrimonio.bl.service;

import it.poste.patrimonio.event.business.impl.gpmfoe.*;

public interface IPositionService {
    void insertSubOrder(SubscriptionOrder order);

    void subOrderFeedback(SubscriptionFeedback feedback);

    void insertRefundOrder(RefundOrder order);

    void refundOrderFeedback(RefundFeedback feedback);

    void insertFundOrder(FundOrderCreation order);

    //11 to 14
    void orderCancellation(Cancellation dto);
}
