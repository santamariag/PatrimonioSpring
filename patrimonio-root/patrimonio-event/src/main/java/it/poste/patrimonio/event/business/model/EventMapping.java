package it.poste.patrimonio.event.business.model;

public class EventMapping {
    public static final String GPEFOE_BUSINESS_EVENT_TYPE_MAPPINGS =
        "business-event.gpm-foe.cancellation-for-charge-failure"   + ":it.poste.patrimonio.event.business.impl.gpmfoe.Cancellation, " +
        "business-event.gpm-foe.cancellation-from-backoffice"      + ":it.poste.patrimonio.event.business.impl.gpmfoe.Cancellation,"+
        "business-event.gpm-foe.cancellation-from-client"          + ":it.poste.patrimonio.event.business.impl.gpmfoe.Cancellation, " +
        "business-event.gpm-foe.cancellation-from-product-company" + ":it.poste.patrimonio.event.business.impl.gpmfoe.Cancellation, " +
        "business-event.gpm-foe.fund-order-creation"               + ":it.poste.patrimonio.event.business.impl.gpmfoe.FundOrderCreation, " +
        "business-event.gpm-foe.master-data-creation"              + ":it.poste.patrimonio.event.business.impl.gpmfoe.MasterDataCreation, " +
        "business-event.gpm-foe.master-data-delete"                + ":it.poste.patrimonio.event.business.impl.gpmfoe.MasterDataDelete, " +
        "business-event.gpm-foe.master-data-lock"                  + ":it.poste.patrimonio.event.business.impl.gpmfoe.MasterDataLock, " +
        "business-event.gpm-foe.pac-creation"                      + ":it.poste.patrimonio.event.business.impl.gpmfoe.PacCreation, " +
        "business-event.gpm-foe.pac-delete"                        + ":it.poste.patrimonio.event.business.impl.gpmfoe.PacDelete, " +
        "business-event.gpm-foe.refund-feedback"                   + ":it.poste.patrimonio.event.business.impl.gpmfoe.RefundFeedback, " +
        "business-event.gpm-foe.refund-order"                      + ":it.poste.patrimonio.event.business.impl.gpmfoe.RefundOrder, " +
        "business-event.gpm-foe.subscription-feedback"             + ":it.poste.patrimonio.event.business.impl.gpmfoe.SubscriptionFeedback, " +
        "business-event.gpm-foe.subscription-order"                + ":it.poste.patrimonio.event.business.impl.gpmfoe.SubscriptionOrder";


    public static final String TITOLI_BUSINESS_EVENT_TYPE_MAPPINGS =
        "business-event.titoli.CTirEvent"         + ":it.poste.patrimonio.event.business.impl.titoli.CtirEvent,"+
        "business-event.titoli.DepositEvent"      + ":it.poste.patrimonio.event.business.impl.titoli.DepositEvent,"+
        "business-event.titoli.ExchangeRateEvent" + ":it.poste.patrimonio.event.business.impl.titoli.ExchangeRateEvent,"+
        "business-event.titoli.PositionEvent"     + ":it.poste.patrimonio.event.business.impl.titoli.PositionEvent,"+
        "business-event.titoli.PriceEvent"        + ":it.poste.patrimonio.event.business.impl.titoli.PriceEvent,"+
        "business-event.titoli.RprzEvent"         + ":it.poste.patrimonio.event.business.impl.titoli.RprzEvent,"+
        "business-event.titoli.TitrEvent"         + ":it.poste.patrimonio.event.business.impl.titoli.TitrEvent";
}
