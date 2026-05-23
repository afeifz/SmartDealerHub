package com.ford.challenge.controller;

import com.ford.challenge.dto.ComparativoRequestDTO;
import com.ford.challenge.model.EspecificacaoFord;
import com.ford.challenge.repository.EspecificacaoFordRepository;
import com.ford.challenge.service.GeminiIntegrationService;
import com.ford.challenge.service.AuditoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/veiculos")
@Tag(name = "Comparativo de Veículos", description = "Endpoints para Inteligência Competitiva")
public class ComparativoController {

    private final EspecificacaoFordRepository repository;
    private final GeminiIntegrationService geminiService;
    private final AuditoriaService auditoriaService;

    public ComparativoController(EspecificacaoFordRepository repository, GeminiIntegrationService geminiService, AuditoriaService auditoriaService) {
        this.repository = repository;
        this.geminiService = geminiService;
        this.auditoriaService = auditoriaService;
    }

    @GetMapping("/ford-specs")
    @Operation(summary = "Lista base oficial", description = "Retorna todas as especificações da Ford importadas do CSV.")
    public ResponseEntity<List<EspecificacaoFord>> getEspecificacoesFord() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("/comparar")
    @Operation(summary = "Compara com a concorrência", description = "Recebe um JSON com o modelo Ford e o concorrente, e usa IA para comparar.")
    public ResponseEntity<String> compararComConcorrente(@Valid @RequestBody ComparativoRequestDTO requestDTO) {

        String versaoFord = requestDTO.getVersaoFord();
        String concorrente = requestDTO.getConcorrente();

        // Extrai o usuário logado via Spring Security para fins de Auditoria (RBAC B2 e B5)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuarioLogado = (auth != null) ? auth.getName() : "SISTEMA";

        // Registra a trilha de auditoria
        auditoriaService.registrarAcessoIA(usuarioLogado, versaoFord, concorrente);

        List<EspecificacaoFord> dadosBase = repository.findAll();
        String resultadoIA = geminiService.gerarComparativo(versaoFord, dadosBase.toString(), concorrente);

        return ResponseEntity.ok(resultadoIA);
    }
}
