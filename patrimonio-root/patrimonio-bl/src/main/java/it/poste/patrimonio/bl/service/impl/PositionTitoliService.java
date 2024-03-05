package it.poste.patrimonio.bl.service.impl;

import it.poste.patrimonio.bl.service.IPositionTitoliService;
import it.poste.patrimonio.event.business.impl.titoli.*;
import it.poste.patrimonio.event.business.model.ITitoliBusinessEvent;
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
    public void processCtirEventFinancialInstrument(CtirEvent message, BusinessEventSender sender) {

        // process events
            System.out.println("PositionTitoliService: processCtirEventFinancialInstrument");

        // update deposits:
            message.setTarget("POSITION");
            sender.send(message);

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
    public void processTitrEventFinancialInstrument(TitrEvent message, BusinessEventSender sender) {


        // Process Event
            System.out.println("PositionTitoliService: processTitrEventFinancialInstrument");
        /*
            -> aggiornare collection titoli con citr = message.getCtir();



        // update deposits:
            -> find all deposits che contengono il titolo ctir

            final int pageLimit = 300;
            int pageNumber = 0;
            Page<T> page = repository.findByCtir(message.getCtir(), new PageRequest(pageNumber, pageLimit));
            while (page.hasNextPage()) {
                processPageContent(page.getContent());
                page = repository.findByCtir(message.getCtir(), new PageRequest(++pageNumber, pageLimit));
            }
            // process last page
            processPageContent(page.getContent());


            message.setTarget("POSITION");
            sender.send(message);

        */
    }

    @Override
    public void processPriceEventFinancialInstrument(PriceEvent message, BusinessEventSender sender) {
        System.out.println("PositionTitoliService: processPriceEventFinancialInstrument");

        // update deposits:
        message.setTarget("POSITION");
        sender.send(message);
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
    public void processRprzEventFinancialInstrument(RprzEvent message, BusinessEventSender sender) {
        System.out.println("PositionTitoliService: processRprzEventFinancialInstrument");

        // update deposits:
        message.setTarget("POSITION");
        sender.send(message);
    }

    @Override
    public void processExchangeRateEventPosition(ExchangeRateEvent message) {
        System.out.println("PositionTitoliService: processExchangeRateEventPosition");
    }

    @Override
    public void processExchangeRateEvent(ExchangeRateEvent message, BusinessEventSender sender) {
        System.out.println("PositionTitoliService: processExchangeRateEvent");

        // update deposits:
        message.setTarget("POSITION");
        sender.send(message);
    }

}
