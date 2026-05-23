package com.ford.challenge.model;

import jakarta.persistence.*;

@Entity
@Table(name = "especificacoes_ford")
public class EspecificacaoFord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoria;
    private String equipamento;
    private String versaoXlt;
    private String versaoLimited;
    private String versaoLimitedPlus;

    // ATENÇÃO: Gere os Getters e Setters aqui (ou use a anotação @Data do Lombok)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getVersaoXlt() {
        return versaoXlt;
    }

    public void setVersaoXlt(String versaoXlt) {
        this.versaoXlt = versaoXlt;
    }

    public String getVersaoLimited() {
        return versaoLimited;
    }

    public void setVersaoLimited(String versaoLimited) {
        this.versaoLimited = versaoLimited;
    }

    public String getVersaoLimitedPlus() {
        return versaoLimitedPlus;
    }

    public void setVersaoLimitedPlus(String versaoLimitedPlus) {
        this.versaoLimitedPlus = versaoLimitedPlus;
    }
}
