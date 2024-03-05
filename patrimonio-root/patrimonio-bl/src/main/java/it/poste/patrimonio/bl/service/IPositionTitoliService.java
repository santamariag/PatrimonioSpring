package it.poste.patrimonio.bl.service;

import it.poste.patrimonio.bl.service.impl.PositionTitoliService;
import it.poste.patrimonio.event.business.impl.titoli.*;
import it.poste.patrimonio.event.business.model.ITitoliBusinessEvent;

import java.util.List;

public interface IPositionTitoliService {


    @FunctionalInterface
    public interface BusinessEventSender {
        boolean send(ITitoliBusinessEvent string);
    }

    // Deposit
        void processDepositEvent(DepositEvent message);

    // Position
        void processPositionEvent(PositionEvent message);

    // Ctir
        void processCtirEventPosition(CtirEvent message);
        void processCtirEventFinancialInstrument(CtirEvent message, BusinessEventSender sender);

    // Titr
        void processTitrEventPosition(TitrEvent message);
        void processTitrEventFinancialInstrument(TitrEvent message, BusinessEventSender sender);

    // Price
        void processPriceEventPosition(PriceEvent message);
        void processPriceEventFinancialInstrument(PriceEvent message, BusinessEventSender sender);

    // Rprz
        void processRprzEventPosition(RprzEvent message);
        void processRprzEventFinancialInstrument(RprzEvent message, BusinessEventSender sender);

    // ExchangeRate
        void processExchangeRateEventPosition(ExchangeRateEvent message);
        void processExchangeRateEvent(ExchangeRateEvent message, BusinessEventSender sender);

}
