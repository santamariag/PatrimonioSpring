package it.poste.patrimonio.bl.service;

import it.poste.patrimonio.event.business.impl.titoli.*;

import java.util.List;

public interface IPositionTitoliService {
    void processDepositEvent(DepositEvent message);

    void processCtirEventPosition(CtirEvent message);

    List<CtirEvent> processCtirEventFinancialInstrument(CtirEvent message);

    void processPositionEvent(PositionEvent message);

    void processTitrEventPosition(TitrEvent message);

    List<CtirEvent> processTitrEventFinancialInstrument(TitrEvent message);

    List<CtirEvent> processPriceEventFinancialInstrument(PriceEvent message);

    void processPriceEventPosition(PriceEvent message);

    void processRprzEventPosition(RprzEvent message);

    List<CtirEvent> processRprzEventFinancialInstrument(RprzEvent message);

    void processExchangeRateEventPosition(ExchangeRateEvent message);

    List<CtirEvent> processExchangeRateEvent(ExchangeRateEvent message);
}
