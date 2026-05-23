package com.ford.challenge.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiIntegrationService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

    public String gerarComparativo(String modeloFord, String dadosFordJson, String carroConcorrente) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String prompt = String.format(
                "Atue como analista automotivo. Tenho os dados da Ford %s: %s. " +
                        "Busque na internet as especificações técnicas da %s e retorne um JSON comparando as duas, usando as mesmas chaves dos dados da Ford. Retorne apenas um JSON apenas com as informações, sem textos complementares",
                modeloFord, dadosFordJson, carroConcorrente
        );

        String requestBody = "{ \"contents\": [{ \"parts\":[{\"text\": \"" + prompt.replace("\"", "\\\"") + "\"}] }] }";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(GEMINI_URL + apiKey, request, String.class);
        return response.getBody();
    }
}