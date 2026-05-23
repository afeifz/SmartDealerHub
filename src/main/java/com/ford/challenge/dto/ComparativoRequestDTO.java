package com.ford.challenge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ComparativoRequestDTO {

    @NotBlank(message = "A versão do veículo Ford é obrigatória.")
    @Size(min = 2, max = 50, message = "O nome da versão Ford excede o tamanho seguro.")
    // Adicionamos \\. e , na regra para permitir "3.0L" e afins
    @Pattern(regexp = "^[a-zA-Z0-9À-ÿ\\s\\-\\+\\.,]+$", message = "Entrada malformada (Sanitização XSS/SQLi).")
    private String versaoFord;

    @NotBlank(message = "O nome do concorrente é obrigatório.")
    @Size(min = 2, max = 50, message = "O nome do concorrente excede o tamanho seguro.")
    @Pattern(regexp = "^[a-zA-Z0-9À-ÿ\\s\\-\\+\\.,]+$", message = "Entrada malformada (Sanitização XSS/SQLi).")
    private String concorrente;

    public ComparativoRequestDTO() {}

    public String getVersaoFord() { return versaoFord; }
    public void setVersaoFord(String versaoFord) { this.versaoFord = versaoFord; }
    public String getConcorrente() { return concorrente; }
    public void setConcorrente(String concorrente) { this.concorrente = concorrente; }
}
