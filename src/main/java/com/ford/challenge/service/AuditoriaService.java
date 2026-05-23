package com.ford.challenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class AuditoriaService {
    private static final Logger auditLogger = LoggerFactory.getLogger("AUDITORIA_SEGURANCA");

    public void registrarAcessoIA(String usuario, String versaoFord, String concorrente) {
        auditLogger.info("LOG DE AUDITORIA | TIMESTAMP: {} | USER: {} | ACTION: CONSULTA_IA | TARGET_FORD: {} | TARGET_CONCORRENTE: {}",
                Instant.now(), usuario, versaoFord, concorrente);
    }
}
