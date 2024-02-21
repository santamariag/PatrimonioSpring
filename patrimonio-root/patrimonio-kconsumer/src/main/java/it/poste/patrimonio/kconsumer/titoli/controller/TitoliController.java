package it.poste.patrimonio.kconsumer.titoli.controller;

import it.poste.patrimonio.event.business.impl.titoli.TestTitoliEvent;
import it.poste.patrimonio.kconsumer.titoli.business.TitoliBusinessEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/titoli")
public class TitoliController {

    @Autowired
    private TitoliBusinessEventProducer titoliBusinessEventProducer;

    @PostMapping("/send")
    public String sendMessage(@RequestParam("message") String message) {
        TestTitoliEvent testTitoliEvent = new TestTitoliEvent();
        testTitoliEvent.setKey(message);
        titoliBusinessEventProducer.sendEvent(testTitoliEvent);
        return "Message sent: " + message;
    }

}