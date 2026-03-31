package com.mballem.demoajax.service;

import com.mballem.demoajax.domain.Emissor;
import com.mballem.demoajax.repository.PromocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@EnableScheduling
@Service
public class NotificacaoService {

    @Autowired
    private PromocaoRepository repository;

    private CopyOnWriteArrayList<Emissor> emissores = new CopyOnWriteArrayList<>();

    public void onOpen(Emissor emissor) throws IOException {
        emissor.getEmiter().send(SseEmitter.event().data(" ").id(emissor.getId()));
    }


    @Scheduled(fixedRate = 25000)
    public void notificar() {
        List<Emissor> emissoresErrors = new ArrayList<>();

        this.emissores.forEach(emissor -> {
            try {
                // 1. Consulta o banco
                Map<String, Object> map = repository.countAndMAxNovasPromocoesByDtCadastro(emissor.getUltimaData());
                long count = (long) map.get("count");

                if (count > 0) {
                    // 2. Tenta enviar a notificação (Isso sim pode gerar IOException)
                    emissor.getEmiter().send(SseEmitter.event()
                            .data(count)
                            .id(emissor.getId()));

                    emissor.setUltimaData((LocalDateTime) map.get("lastDate"));
                }
            } catch (Exception e) { // <--- Use Exception genérica para capturar erros de banco E de envio
                emissoresErrors.add(emissor);
            }
        });

        this.emissores.removeAll(emissoresErrors);
    }

    public void addEmissor(Emissor emissor) throws IOException {
        this.emissores.add(emissor);
    }

    public void removeEmissor(Emissor emissor) throws IOException {
        this.emissores.remove(emissor);
    }


    public CopyOnWriteArrayList<Emissor> getEmissores() {
        return emissores;
    }


}
