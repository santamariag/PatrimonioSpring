package it.poste.patrimonio.bl.service.impl;

import it.poste.patrimonio.bl.service.IPositionTitoliService;
import it.poste.patrimonio.event.business.impl.titoli.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionTitoliService implements IPositionTitoliService {

    @Override
    public void processDepositEvent(DepositEvent message) {
        System.out.println("PositionTitoliService: processDepositEvent");
    }

    @Override
    public void processCtirEventPosition(CtirEvent message) {
        System.out.println("PositionTitoliService: processCtirEventPosition");
    }

    @Override
    public List<CtirEvent> processCtirEventFinancialInstrument(CtirEvent message) {
        System.out.println("PositionTitoliService: processCtirEventFinancialInstrument");
        return null;
    }

    @Override
    public void processPositionEvent(PositionEvent message) {
        System.out.println("PositionTitoliService: processPositionEvent");
    }

    @Override
    public void processTitrEventPosition(TitrEvent message) {
        System.out.println("PositionTitoliService: processTitrEventPosition");
    }

    @Override
    public List<CtirEvent> processTitrEventFinancialInstrument(TitrEvent message) {
        System.out.println("PositionTitoliService: processTitrEventFinancialInstrument");
        return null;
    }

    @Override
    public List<CtirEvent> processPriceEventFinancialInstrument(PriceEvent message) {
        System.out.println("PositionTitoliService: processPriceEventFinancialInstrument");
        return null;
    }

    @Override
    public void processPriceEventPosition(PriceEvent message) {
        System.out.println("PositionTitoliService: processPriceEventPosition");
    }

    @Override
    public void processRprzEventPosition(RprzEvent message) {
        System.out.println("PositionTitoliService: processRprzEventPosition");
    }

    @Override
    public List<CtirEvent> processRprzEventFinancialInstrument(RprzEvent message) {
        System.out.println("PositionTitoliService: processRprzEventFinancialInstrument");
        return null;
    }

    @Override
    public void processExchangeRateEventPosition(ExchangeRateEvent message) {
        System.out.println("PositionTitoliService: processExchangeRateEventPosition");
    }

    @Override
    public List<CtirEvent> processExchangeRateEvent(ExchangeRateEvent message) {
        System.out.println("PositionTitoliService: processExchangeRateEvent");
        return null;
    }

}
