package com.mballem.demoajax.web.controller;

import com.mballem.demoajax.domain.Emissor;
import com.mballem.demoajax.repository.PromocaoRepository;
import com.mballem.demoajax.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class NotificacaoController {

    @Autowired
    private PromocaoRepository repository;
    @Autowired
    NotificacaoService service;

    @GetMapping("/promocao/notificacao")
    public SseEmitter enviarNotificacao() throws IOException {

        SseEmitter emitter = new SseEmitter(0L);

        Emissor emissor = new Emissor(emitter, getDtCadastroUltimaPromocao());

        service.onOpen(emissor);
        service.addEmissor(emissor);

        emissor.getEmiter().onCompletion(() -> {
            try {
                service.removeEmissor(emissor);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(">size after add: " + service.getEmissores().size());
        return emissor.getEmiter();
    }

    public LocalDateTime getDtCadastroUltimaPromocao() {

        return repository.findPromocaoComUltimaData();
    }
}
