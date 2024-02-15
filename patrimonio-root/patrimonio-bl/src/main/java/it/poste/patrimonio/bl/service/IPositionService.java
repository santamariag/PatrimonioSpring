package it.poste.patrimonio.bl.service;

import it.poste.patrimonio.event.business.model.gpmfoe.*;

public interface IPositionService {
        void insertSubOrder(SubscriptionOrder order);

        void subOrderFeedback(SubscriptionFeedback feedback);

        void insertRefundOrder(RefundOrder order);

        void refundOrderFeedback(RefundFeedback feedback);

        void orderCancellationFromBackOffice(CancellationFromBackOffice canc);

        void orderCancellationFromClient(CancellationFromClient canc);

        void orderCancellationForChareFailure(CancellationForChargeFailure canc);

        void orderCancellationFromProductCompany(CancellationFromProductCompany canc);

        void insertFundOrder(FundOrderCreation order);
}
